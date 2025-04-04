package ru.nsu.pozhidaev.worker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class Worker {
    private static final int PORT = 5005;
    private static final String RECEIVE_MESSAGE = "WSUP?";
    private static final String SEND_MESSAGE = "YO_MAN!";
    private static MulticastSocket udpSocket;
    private static InetAddress udpGroup;
    private static InetAddress manager;
    public static void main(String[] args) throws Exception {
        activate();
        findManager();
    }

    private static void activate() throws IOException {
        udpSocket = new MulticastSocket(PORT);
        udpGroup = InetAddress.getByName("224.0.0.1");
        udpSocket.joinGroup(udpGroup);
        System.out.println("Client listening on port 5005...");
    }

    private static void findManager() throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            if(received.equals(RECEIVE_MESSAGE)) {
                System.out.println("Found Manager");
                break;
            }
            manager = packet.getAddress();
            byte[] sendBuffer = SEND_MESSAGE.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                    manager, PORT);
            udpSocket.send(sendPacket);
        }
    }
}
