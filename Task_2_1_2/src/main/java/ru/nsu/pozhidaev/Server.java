package ru.nsu.pozhidaev;

import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            int port = 12345;

            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Ожидание UDP-сообщений на порту " + port + "...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Получено от " + packet.getAddress() + ":" + packet.getPort());
                System.out.println("Сообщение: " + received);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
