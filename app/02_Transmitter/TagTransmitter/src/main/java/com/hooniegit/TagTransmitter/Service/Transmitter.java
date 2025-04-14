package com.hooniegit.TagTransmitter.Service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 */
public class Transmitter {

    private static DatagramSocket[] clientSocket = new DatagramSocket[64];
    private static final AtomicInteger index = new AtomicInteger(0);
    private static final InetAddress[] addresses = new InetAddress[4];

    static {
        try {
            // Add Socket Address Here
            addresses[0] = InetAddress.getByName("localhost");
        } catch (Exception e) {}
    }

    /**
     * UDP Transmitter
     * @param bytes
     * @param address
     * @param port
     * @throws Exception
     */
    public static void udp(byte[] bytes, InetAddress address, int port) throws Exception {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
        clientSocket[index.getAndUpdate(i -> (i + 1) % 64)].send(packet);
    }

}
