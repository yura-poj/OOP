package ru.nsu.pozhidaev.manager;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * The Manager class implements the logic for managing workers in a distributed number checking system.
 * 
 * <p>The Manager performs the following functions:
 * <ul>
 *   <li>Finding available workers via UDP broadcast</li>
 *   <li>Establishing TCP connections with found workers</li>
 *   <li>Distributing tasks among workers</li>
 *   <li>Collecting and processing results from workers</li>
 * </ul>
 * </p>
 * 
 * <p>Workflow:
 * <ol>
 *   <li>Initializing UDP socket for broadcast messages</li>
 *   <li>Sending broadcast message to find workers</li>
 *   <li>Waiting for worker responses</li>
 *   <li>Establishing TCP connections with found workers</li>
 *   <li>Distributing array of numbers among workers</li>
 *   <li>Waiting for results from all workers</li>
 *   <li>Releasing resources and completing work</li>
 * </ol>
 * </p>
 * 
 * <p>The Manager uses a combination of UDP for discovery and TCP for reliable communication.
 * It implements a fault-tolerant system that can handle worker failures and redistribute tasks if needed.</p>
 */
public class Manager {
    private static final String SEND_MESSAGE = "WSUP?";
    private static final String RECEIVE_MESSAGE = "YO_MAN!";
    private static final String GROUP_IP = "224.0.0.1";
    private static final int PORT = 5005;
    private HashSet<InetAddress> workers = new HashSet<>();
    private InetAddress udpGroup;
    private DatagramSocket udpSocket;
    private ArrayList<WorkerServer> workerServers = new ArrayList<>();
    @Getter
    private AtomicBoolean result = new AtomicBoolean(false);
    @Getter
    private final Object lock = new Object();

    @Getter
    private final Object workersLock = new Object();

    @Getter
    private final Object unfinishedLock = new Object();

    private ServerSocket serverSocket;
    @Getter
    private volatile ArrayList<Integer> unfinished = new ArrayList<>();


    public boolean work (int[] task) {

        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(1000);
            activate();
            sendBroadcast(SEND_MESSAGE);
            findWorkers();
            startTcpConnections();
            setTasks(task);
            freeSlaves();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Manager error");
        } catch (InterruptedException e) {
            System.out.println("Interrapted");
        }
        return result.get();
    }

    private void activate() throws UnknownHostException, SocketException {
        udpGroup = InetAddress.getByName(GROUP_IP);
        udpSocket = new DatagramSocket();
        udpSocket.setSoTimeout(1000);
        System.out.println("Listening on: " + udpSocket.getLocalAddress() + ":" + udpSocket.getLocalPort());
    }

    private void sendBroadcast(String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, udpGroup, PORT);
        udpSocket.send(packet);
    }

    private void findWorkers() {
        long currentTimeMillis = System.currentTimeMillis();
        long waitTime = 1000;
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (currentTimeMillis + waitTime > System.currentTimeMillis()) {
            try {
                udpSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                if (received.equals(RECEIVE_MESSAGE)) {
                    workers.add(packet.getAddress());
                    System.out.println("Find worker: " + packet.getAddress().getHostAddress());
                }
            } catch (IOException e) {
                System.out.println("finish search for workers");
            }
        }
        udpSocket.close();
    }

    private void startTcpConnections() throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        long waitTime = 3000;
        Socket socket = null;
        while (currentTimeMillis + waitTime > System.currentTimeMillis()) {
            try {
                socket = serverSocket.accept();
                System.out.println("I was called");
                if (workers.contains(socket.getInetAddress())) {
                    workerServers.add(new WorkerServer(socket, this));
                    System.out.println("Connect with " + socket.getInetAddress());

                } else {
                    socket.close();
                }
            } catch (SocketTimeoutException e) {
                System.out.println("Socket timed out");
            }
        }
    }

    private void setTasks(int[] task) throws InterruptedException {
        synchronized (unfinishedLock) {
            unfinished.clear();
        }
        synchronized (workersLock) {
            if (workerServers.isEmpty()) {
                System.out.println("No workers found");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing server socket");
                }
                work(task);
            }
            int baseSize = task.length / workerServers.size();
            int reminder = task.length % workerServers.size();
            int start = 0;
            int end = 0;

            for (int i = 0; i < workerServers.size(); i++) {
                end += baseSize;
                if (reminder > 0) {
                    end++;
                    reminder--;
                }
                int[] part = Arrays.copyOfRange(task, start, end);
                ArrayList<Integer> taskList = Arrays.stream(part)
                        .boxed()
                        .collect(Collectors.toCollection(ArrayList::new));
                workerServers.get(i).setTask(new ArrayList<>(taskList));
                System.out.println("send task from " + start + " to " + end + "for " + i);
                workerServers.get(i).send(part);
                start = end;
            }
        }
        waitWorkers();
    }

    private void waitWorkers() throws InterruptedException {
        synchronized (lock) {
            int numberCalled = 1;
            while (numberCalled <= workerServers.size() && !result.get()) {
                lock.wait();
                numberCalled++;
            }
        }
        synchronized (unfinishedLock) {
            if(!result.get() && !unfinished.isEmpty()) {
                System.out.println("Not finished:"  + unfinished.toString());
                setTasks(unfinished.stream()
                        .mapToInt(Integer::intValue)
                        .toArray());
            }
        }
    }

    public void removeWorker(WorkerServer workerServer) {
            synchronized (workersLock) {
                workerServers.remove(workerServer);
            }
        System.out.println("remove worker");
    }

    private void freeSlaves() {
        for (WorkerServer workerServer : workerServers) {
            workerServer.finish();
        }
        System.out.println("freeSlaves");
    }

    class WorkerServer extends Thread {

        private Socket socket;
        private BufferedReader in;
        private ObjectOutputStream out;
        private Manager manager;
        @Getter
        @Setter
        private volatile ArrayList<Integer> task = new ArrayList<>();

        public WorkerServer(Socket socket, Manager manager) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.manager = manager;
            start();
        }

        @Override
        public void run() {
            String word;
            try {
                while (true) {
                    word = in.readLine();
                    System.out.println(word);
                    switch (word) {
                        case "TRUE":
                            manager.getResult().compareAndSet(false, true);
                            synchronized (manager.getLock()) {
                                manager.getLock().notify();
                            }
                            return;
                        case "FALSE":
                            synchronized (manager.getLock()) {
                                manager.getLock().notify();
                            }
                            return;
                        case null:
                            lostWorker();
                            return;
                        default:
                            System.out.println("Unknown command: " + word);
                    }

                }
            } catch (IOException e) {
                lostWorker();
            }
        }

        private void send(int[] task) {
            try {
                out.writeObject(task);
                out.flush();
            } catch (IOException e) {
                lostWorker();
                e.printStackTrace();
            }
        }

        public void finish() {
            send(new int[] {});
            try {
                socket.close();
            } catch (IOException ignore) {}
        }

        private void lostWorker() {
            synchronized (manager.getUnfinishedLock()) {
                manager.getUnfinished().addAll(getTask());
            }
            synchronized (manager.getWorkersLock()) {
                manager.removeWorker(this);
            }
            System.out.println("Worker was lost: " + socket.getInetAddress());
            try {
                socket.close();
            } catch (IOException ignore) {
            }
            synchronized (manager.getLock()) {
                manager.getLock().notify();
            }
        }
    }
}
