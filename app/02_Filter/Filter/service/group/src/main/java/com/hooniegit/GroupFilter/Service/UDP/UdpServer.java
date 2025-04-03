package com.hooniegit.GroupFilter.Service.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import com.hooniegit.GroupFilter.Service.MSSQL.ReferenceMap;

// Nexus Imports
import com.hooniegit.SourceData.Tag.TagGroup;
import com.hooniegit.SourceData.Tag.TagData;
import com.hooniegit.Xerializer.Serializer.KryoSerializer;

@Service
public class UdpServer {

    private final ReferenceMap map;
    private static final int PORT = 13002;
    private static final int BUFFER_SIZE = 65507;

    @Autowired
    public UdpServer(ReferenceMap map) {
        this.map = map;
    }

    @PostConstruct
    private void run() {

        DatagramSocket serverSocket = null;

        ConcurrentHashMap<Integer, Integer> reference = map.getReference();

        try {
            serverSocket = new DatagramSocket(PORT);
            byte[] receiveBuffer = new byte[BUFFER_SIZE];

            while (true) {
                // 클라이언트로부터 데이터 수신
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                TagGroup<Integer> tagGroup = KryoSerializer.deserialize(receivePacket.getData());
                TagData<Integer>[] tagData = tagGroup.getTagData();

                

            }
        } catch (Exception e) {
            System.err.println("UDP 서버 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("UDP 서버 소켓 종료.");
            }
        }

    }

}
