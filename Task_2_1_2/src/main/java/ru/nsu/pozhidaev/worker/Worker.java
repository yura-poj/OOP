package ru.nsu.pozhidaev.worker;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Worker {
    private static final int PORT = 5005;
    private static final String RECEIVE_MESSAGE = "WSUP?";
    private static final String SEND_MESSAGE = "YO_MAN!";
    private static MulticastSocket udpSocket;
    private static InetAddress udpGroup;
    private static InetSocketAddress manager;
    public static void main(String[] args) throws Exception {
        activate();
        findManager();
    }

    private static void activate() throws IOException {
        udpSocket = new MulticastSocket(PORT);
        udpSocket.setReuseAddress(true);
        udpGroup = InetAddress.getByName("224.0.0.1");
        udpSocket.joinGroup(udpGroup);
        System.out.println("Listening on: " + udpSocket.getLocalAddress() + ":" + udpSocket.getLocalPort());
    }

    private static void findManager() throws IOException {
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
        System.out.println("send message to Manager");
        manager = new InetSocketAddress(packet.getAddress(), packet.getPort());        byte[] sendBuffer = SEND_MESSAGE.getBytes(StandardCharsets.UTF_8);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                manager.getAddress(), manager.getPort());
        udpSocket.send(sendPacket);
    }
}
