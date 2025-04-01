package ru.nsu.pozhidaev.manager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Manager {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        String message = "Hello from Java UDP Server!";
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        InetAddress broadcastAddress = InetAddress.getByName("172.28.0.255");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, 5005);
            socket.send(packet);
            System.out.println("Broadcast sent: " + message);
            Thread.sleep(2000);
        }
    }
}
