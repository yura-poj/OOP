package ru.nsu.pozhidaev.worker;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import ru.nsu.pozhidaev.primeDetecter.PrimeNumberDetector;

public class Worker {
    private static final int PORT = 5005;
    private static final String GROUP_IP = "224.0.0.1";
    private static final String RECEIVE_MESSAGE = "WSUP?";
    private static final String SEND_MESSAGE = "YO_MAN!";
    private MulticastSocket udpSocket;
    private InetAddress udpGroup;
    private InetSocketAddress manager;
    public Worker() {
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
        System.out.println("create socket");
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        int[] task = (int[]) in.readObject();
        System.out.println("get task: " + Arrays.toString(task));        PrimeNumberDetector primeDetector = new PrimeNumberDetector();
        boolean result =  primeDetector.isPrimeNumberExist(task);
        if(result) {
            out.write("TRUE\n");
        } else {
            out.write("FALSE\n");
        }
        System.out.println("send message to Manager: " + result);
        out.flush();
        in.close();
        out.close();
        socket.close();
        System.out.println("socket closed");
    }
}
