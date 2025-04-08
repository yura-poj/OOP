package ru.nsu.pozhidaev.manager;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

public class Manager {
    private static final String SEND_MESSAGE = "WSUP?";
    private static final String RECEIVE_MESSAGE = "YO_MAN!";
    private static final int PORT = 5005;
    private static HashSet<InetAddress> workers = new HashSet<>();
    private static InetAddress udpGroup;
    private static DatagramSocket udpSocket;
    public static void main(String[] args) throws Exception {
        activate();
        sendBroadcast(SEND_MESSAGE);
        findWorkers();


    }

    private static void activate() throws UnknownHostException, SocketException {
        udpGroup = InetAddress.getByName("224.0.0.1");
        udpSocket = new DatagramSocket();
        udpSocket.setSoTimeout(1000);
        System.out.println("Listening on: " + udpSocket.getLocalAddress() + ":" + udpSocket.getLocalPort());    }

    private static void sendBroadcast(String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, udpGroup, PORT);
        udpSocket.send(packet);
    }

    private static void findWorkers() {
        long currentTimeMillis = System.currentTimeMillis();
        long waitTime = 10000;
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (currentTimeMillis + waitTime> System.currentTimeMillis()) {
            try {
                udpSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                if(received.equals(RECEIVE_MESSAGE)) {
                    workers.add(InetAddress.getByName(packet.getAddress().getHostAddress()));
                    System.out.println("Find worker: " + packet.getAddress().getHostAddress());
                }
            } catch (IOException e) {
                System.out.println("finish search for workers");
            }
        }
    }

    private static void initTcpSocket() throws IOException {

    }
}
