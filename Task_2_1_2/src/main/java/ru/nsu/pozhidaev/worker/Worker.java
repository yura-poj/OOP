package ru.nsu.pozhidaev.worker;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Worker {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(5005);
        byte[] buffer = new byte[1024];
        System.out.println("Client listening on port 5005...");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received from " + packet.getAddress() + ": " + received);
        }
    }
}
