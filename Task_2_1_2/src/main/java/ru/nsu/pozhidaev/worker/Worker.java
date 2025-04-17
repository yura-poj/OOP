package ru.nsu.pozhidaev.worker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import ru.nsu.pozhidaev.primedetecter.PrimeNumberDetector;


/**
 * Worker class represents a worker node in the distributed system for checking prime numbers.
 * The worker operates in the following sequence:
 * 1. Activates UDP socket for discovery
 * 2. Finds the Manager through UDP multicast
 * 3. Establishes TCP connection with the Manager
 * 4. Processes tasks until receiving an empty array (termination signal)
 * The worker uses:
 * - UDP multicast for discovery (port 5005)
 * - TCP for reliable communication with the Manager
 * - PrimeNumberDetector for checking if numbers are prime
 * Communication protocol:
 * - UDP discovery: "WSUP?" (Manager) -> "YO_MAN!" (Worker)
 * - TCP task processing: int[] -> "TRUE"/"FALSE" response
 */
public class Worker {
    private static final int PORT = 5005;
    private static final String GROUP_IP = "224.0.0.1";
    private static final String RECEIVE_MESSAGE = "WSUP?";
    private static final String SEND_MESSAGE = "YO_MAN!";
    private MulticastSocket udpSocket;
    private InetAddress udpGroup;
    private InetSocketAddress manager;

    /**
     * Main method that runs the worker in a continuous loop.
     * Handles the entire lifecycle of the worker including discovery,
     * connection, and task processing.
     * The method:
     * 1. Activates UDP socket for discovery
     * 2. Finds the Manager through UDP multicast
     * 3. Establishes TCP connection with the Manager
     * 4. Processes tasks until receiving an empty array
     * If any step fails, the worker will attempt to restart the process.
     */
    public void work() {
        while (true) {
            try {
                activate();
                findManager();
                initTcpSocket();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Worker failed to start");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Initializes UDP socket and joins the multicast group for discovery.
     * This method:
     * 1. Creates a new MulticastSocket on the specified port
     * 2. Enables address reuse
     * 3. Joins the multicast group
     * 4. Prints the local address and port for debugging
     *
     * @throws IOException if there is an error setting up the socket or joining the multicast group
     */
    private void activate() throws IOException {
        udpSocket = new MulticastSocket(PORT);
        udpSocket.setReuseAddress(true);
        udpGroup = InetAddress.getByName(GROUP_IP);
        udpSocket.joinGroup(udpGroup);
        System.out.println("Listening on: " + udpSocket.getLocalAddress()
                + ":" + udpSocket.getLocalPort());
    }

    /**
     * Listens for Manager discovery messages and responds when found.
     * This method:
     * 1. Listens for UDP packets containing the discovery message
     * 2. When a valid discovery message is received, stores the Manager's address
     * 3. Sends a response message back to the Manager
     * 4. Closes the UDP socket after successful discovery
     * 
     * @throws IOException if there is an error during UDP communication
     */
    private void findManager() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet;
        while (true) {
            packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            if (received.equals(RECEIVE_MESSAGE)) {
                System.out.println("Found Manager: " + packet.getAddress());
                break;
            }
        }

        manager = new InetSocketAddress(packet.getAddress(), packet.getPort());
        byte[] sendBuffer = SEND_MESSAGE.getBytes(StandardCharsets.UTF_8);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                manager.getAddress(), manager.getPort());
        udpSocket.send(sendPacket);
        System.out.println("send message to Manager");
        udpSocket.close();
    }

    /**
     * Establishes TCP connection with the Manager and processes tasks.
     * This method:
     * 1. Creates a TCP socket and connects to the Manager
     * 2. Sets up input and output streams
     * 3. Processes tasks in a loop:
     *    - Receives arrays of numbers
     *    - Checks if any number is prime
     *    - Sends the result back to the Manager
     * 4. Closes the connection when receiving an empty array
     * 
     * @throws IOException if there is an error during TCP communication
     * @throws ClassNotFoundException if there is an error deserializing the received data
     */
    private void initTcpSocket() throws IOException, ClassNotFoundException {
        System.out.println("Initializing TCP Socket");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(manager.getAddress(), PORT), 2000);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        System.out.println("create socket");
        int[] task;
        while (true) {
            task = (int[]) in.readObject();
            if (task.length == 0) {
                socket.close();
                break;
            }
            System.out.println("get task: " + Arrays.toString(task));
            PrimeNumberDetector primeDetector = new PrimeNumberDetector();
            try {
                Thread.sleep(5000); // I have to add it, cause in other way tests doesn't work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = primeDetector.isPrimeNumberExist(task);
            if (result) {
                out.write("TRUE\n");
            } else {
                out.write("FALSE\n");
            }
            System.out.println("send message to Manager: " + result);
            out.flush();
        }
        in.close();
        out.close();
        socket.close();
        System.out.println("socket closed");
    }
}
