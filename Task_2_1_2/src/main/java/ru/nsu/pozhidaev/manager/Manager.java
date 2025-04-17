package ru.nsu.pozhidaev.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

/**
 * Manager class coordinates workers in a distributed prime number detection system.
 * The Manager performs the following functions:
 * - Finding available workers via UDP broadcast
 * - Establishing TCP connections with found workers
 * - Distributing tasks among workers
 * - Collecting and processing results from workers
 * Workflow:
 * 1. Initialize UDP socket for broadcast messages
 * 2. Send broadcast message to find workers
 * 3. Wait for worker responses
 * 4. Establish TCP connections with found workers
 * 5. Distribute array of numbers among workers
 * 6. Wait for results from all workers
 * 7. Release resources and complete work
 * The Manager uses:
 * - UDP multicast for discovery (port 5005)
 * - TCP for reliable communication with workers
 * - PrimeNumberDetector for checking results
 * Communication protocol:
 * - UDP discovery: "WSUP?" (Manager) -> "YO_MAN!" (Worker)
 * - TCP task processing: int[] -> "TRUE"/"FALSE" response
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

    /**
     * Main method that coordinates the distributed processing of numbers.
     * The method:
     * 1. Initializes server socket
     * 2. Activates UDP discovery
     * 3. Finds available workers
     * 4. Establishes TCP connections
     * 5. Distributes tasks
     * 6. Waits for results
     * 7. Cleans up resources
     *
     * @param task Array of numbers to be processed
     *
     * @return true if any number is prime, false otherwise
     */
    public boolean work(int[] task) {
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
            System.out.println("Interrupted");
        }
        return result.get();
    }

    /**
     * Initializes UDP socket and prepares for worker discovery.
     * This method:
     * 1. Creates a new DatagramSocket
     * 2. Sets socket timeout
     * 3. Prints local address and port for debugging
     *
     * @throws UnknownHostException if the multicast group address is invalid
     * @throws SocketException if there is an error creating the socket
     */
    private void activate() throws UnknownHostException, SocketException {
        udpGroup = InetAddress.getByName(GROUP_IP);
        udpSocket = new DatagramSocket();
        udpSocket.setSoTimeout(1000);
        System.out.println("Listening on: " + udpSocket.getLocalAddress()
                + ":" + udpSocket.getLocalPort());
    }

    /**
     * Sends a broadcast message to discover workers.
     *
     * @param message The discovery message to send
     *
     * @throws IOException if there is an error sending the message
     */
    private void sendBroadcast(String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, udpGroup, PORT);
        udpSocket.send(packet);
    }

    /**
     * Listens for worker responses to the discovery message.
     * This method:
     * 1. Listens for UDP packets for a specified duration
     * 2. Processes worker responses
     * 3. Stores worker addresses
     * 4. Closes the UDP socket when done
     */
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

    /**
     * Establishes TCP connections with discovered workers.
     * This method:
     * 1. Accepts TCP connections from workers
     * 2. Verifies worker addresses
     * 3. Creates WorkerServer instances for each connection
     * 4. Handles connection timeouts
     *
     * @throws IOException if there is an error establishing connections
     */
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

    /**
     * Distributes tasks among available workers.
     * This method:
     * 1. Clears unfinished tasks list
     * 2. Checks for available workers
     * 3. Divides tasks among workers
     * 4. Sends tasks to workers
     * 5. Waits for results
     *
     * @param task Array of numbers to be processed
     *
     * @throws InterruptedException if the waiting thread is interrupted
     */
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

    /**
     * Waits for results from all workers.
     * This method:
     * 1. Waits for all workers to complete
     * 2. Checks for unfinished tasks
     * 3. Redistributes unfinished tasks if needed
     *
     * @throws InterruptedException if the waiting thread is interrupted
     */
    private void waitWorkers() throws InterruptedException {
        synchronized (lock) {
            int numberCalled = 1;
            while (numberCalled <= workerServers.size() && !result.get()) {
                lock.wait();
                numberCalled++;
            }
        }
        synchronized (unfinishedLock) {
            if (!result.get() && !unfinished.isEmpty()) {
                System.out.println("Not finished:" + unfinished.toString());
                setTasks(unfinished.stream()
                        .mapToInt(Integer::intValue)
                        .toArray());
            }
        }
    }

    /**
     * Removes a worker from the active workers list.
     *
     * @param workerServer The worker server to remove
     */
    public void removeWorker(WorkerServer workerServer) {
        synchronized (workersLock) {
            workerServers.remove(workerServer);
        }
        System.out.println("remove worker");
    }

    /**
     * Releases all worker connections and resources.
     * This method:
     * 1. Sends termination signal to all workers
     * 2. Closes all worker connections
     */
    private void freeSlaves() {
        for (WorkerServer workerServer : workerServers) {
            workerServer.finish();
        }
        System.out.println("freeSlaves");
    }

    /**
     * WorkerServer class represents a connected worker in the distributed system.
     * This class:
     * - Maintains TCP connection with a worker
     * - Handles task distribution
     * - Processes worker responses
     * - Manages worker lifecycle
     */
    class WorkerServer extends Thread {
        private Socket socket;
        private BufferedReader in;
        private ObjectOutputStream out;
        private Manager manager;
        @Getter
        @Setter
        private volatile ArrayList<Integer> task = new ArrayList<>();

        /**
         * Creates a new WorkerServer instance.
         *
         * @param socket The TCP socket connected to the worker
         * @param manager The parent Manager instance
         *
         * @throws IOException if there is an error setting up the connection
         */
        public WorkerServer(Socket socket, Manager manager) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.manager = manager;
            start();
        }

        /**
         * Main processing loop for the worker server.
         * This method:
         * 1. Reads responses from the worker
         * 2. Processes results
         * 3. Updates the manager's state
         * 4. Handles worker disconnection
         */
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

        /**
         * Sends a task to the worker.
         *
         * @param task Array of numbers to be processed
         */
        private void send(int[] task) {
            try {
                out.writeObject(task);
                out.flush();
            } catch (IOException e) {
                lostWorker();
                e.printStackTrace();
            }
        }

        /**
         * Sends termination signal to the worker and closes the connection.
         */
        public void finish() {
            send(new int[]{});
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Handles worker disconnection.
         * This method:
         * 1. Adds unfinished tasks back to the manager
         * 2. Removes the worker from active workers
         * 3. Closes the connection
         * 4. Notifies waiting threads
         */
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (manager.getLock()) {
                manager.getLock().notify();
            }
        }
    }
}
