package ru.nsu.pozhidaev.manager;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class Manager {
    private static final String SEND_MESSAGE = "WSUP?";
    private static final String RECEIVE_MESSAGE = "YO_MAN!";
    private static final String GROUP_IP = "224.0.0.1";
    private static final int PORT = 5005;
    private HashSet<InetAddress> workers = new HashSet<>();
    private InetAddress udpGroup;
    private DatagramSocket udpSocket;
    private ArrayList<WorkerServer> workerServers = new ArrayList<>();
    AtomicBoolean result = new AtomicBoolean(false);
    private final Object lock = new Object();
    ServerSocket serverSocket;


    public boolean work (int[] task) {

        try {
            serverSocket = new ServerSocket(PORT);
            activate();
            sendBroadcast(SEND_MESSAGE);
            findWorkers();
            initTcpSocket();
            setTasks(task);
            waitWorkers();


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

    private void initTcpSocket() throws IOException {
        serverSocket.setSoTimeout(1000);
        long currentTimeMillis = System.currentTimeMillis();
        long waitTime = 3000;
        Socket socket = null;
        while (currentTimeMillis + waitTime > System.currentTimeMillis()) {
            try {
                socket = serverSocket.accept();
                System.out.println("I was called");
                if (workers.contains(socket.getInetAddress())) {
                    workerServers.add(new WorkerServer(socket, result, lock));
                    System.out.println("Connect with " + socket.getInetAddress());

                } else {
                    socket.close();
                }
            } catch (SocketTimeoutException e) {
                System.out.println("Socket timed out");
            }
        }
    }

    private void setTasks(int[] task) {
        System.out.println(Arrays.toString(task));
        int baseSize = task.length / workerServers.size();
        int reminder = task.length % workerServers.size();
        int start = 0;
        int end = 0;

        for(int i = 0; i < workerServers.size(); i++) {
            end += baseSize;
            if (reminder > 0) {
                end++;
                reminder--;
            }
            System.out.println("send task from " + start + " to " + end + "for " + i);
            workerServers.get(i).send(Arrays.copyOfRange(task, start, end));
            start = end;
        }
    }

    private void waitWorkers() throws InterruptedException {
        synchronized (lock) {
            int numberCalled = 1;
            while (numberCalled < workerServers.size()) {
                lock.wait();
                numberCalled++;
            }
        }
    }

    class WorkerServer extends Thread {

        private final Object lock;
        private Socket socket;
        private BufferedReader in;
        private ObjectOutputStream out;
        private AtomicBoolean result = new AtomicBoolean(false);

        public WorkerServer(Socket socket, AtomicBoolean result, Object lock) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.result = result;
            this.lock = lock;
            start();
        }

        @Override
        public void run() {
            String word;
            try {
                while (true) {
                    word = in.readLine();
                    switch (word) {
                        case "TRUE":
                            synchronized (lock) {
                                result.compareAndSet(false, true);
                                lock.notify();
                            }
                            return;
                        case "FALSE":
                            return;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void send(int[] task) {
            try {
                out.writeObject(task);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
