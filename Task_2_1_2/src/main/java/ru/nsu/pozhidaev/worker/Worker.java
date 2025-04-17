package ru.nsu.pozhidaev.worker;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import ru.nsu.pozhidaev.primeDetecter.PrimeNumberDetector;

/**
 * The Worker class implements the worker node functionality in the distributed system.
 * 
 * <p>A Worker is responsible for:
 * <ul>
 *   <li>Listening for Manager discovery messages via UDP multicast</li>
 *   <li>Responding to Manager discovery requests</li>
 *   <li>Establishing TCP connection with the Manager</li>
 *   <li>Receiving and processing number arrays</li>
 *   <li>Checking if any number in the array is prime</li>
 *   <li>Sending results back to the Manager</li>
 * </ul>
 * </p>
 * 
 * <p>The Worker operates in a continuous loop:
 * <ol>
 *   <li>Activates UDP socket for discovery</li>
 *   <li>Finds the Manager through UDP multicast</li>
 *   <li>Establishes TCP connection with the Manager</li>
 *   <li>Processes tasks until receiving an empty array (termination signal)</li>
 * </ol>
 * </p>
 * 
 * <p>The Worker uses UDP multicast for discovery and TCP for reliable communication with the Manager.
 * It implements a robust error handling mechanism to ensure continuous operation.</p>
 */
public class Worker {
    private static final int PORT = 5005;
    private static final String GROUP_IP = "224.0.0.1";
    private static final String RECEIVE_MESSAGE = "WSUP?";
    private static final String SEND_MESSAGE = "YO_MAN!";
    private MulticastSocket udpSocket;
    private InetAddress udpGroup;
    private InetSocketAddress manager;

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

    private void activate() throws IOException {
        udpSocket = new MulticastSocket(PORT);
        udpSocket.setReuseAddress(true);
        udpGroup = InetAddress.getByName(GROUP_IP);
        udpSocket.joinGroup(udpGroup);
        System.out.println("Listening on: " + udpSocket.getLocalAddress() + ":" + udpSocket.getLocalPort());
    }

    private void findManager() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet;
        while (true) {
            packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            if(received.equals(RECEIVE_MESSAGE)) {
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

    private void initTcpSocket() throws IOException, ClassNotFoundException {
        System.out.println("Initializing TCP Socket");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(manager.getAddress(), PORT), 2000);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());;
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        System.out.println("create socket");
        int[] task;
        while (true) {
             task = (int[]) in.readObject();
            if( task.length == 0) {
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
