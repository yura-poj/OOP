package ru.nsu.pozhidaev;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainServer {
    public static void main(String[] args) {
        try {
            String message = "Привет всем в сети!";
            byte[] buffer = message.getBytes();

            InetAddress broadcastAddress = InetAddress.getByName("127.0.0.1");
            int port = 12345;

            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);
            for(int i = 0; i < 10; i++){
                socket.send(packet);
                System.out.println("Broadcast-сообщение отправлено.");
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
