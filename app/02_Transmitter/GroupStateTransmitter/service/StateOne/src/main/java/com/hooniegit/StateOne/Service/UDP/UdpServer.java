package com.hooniegit.StateOne.Service.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import com.hooniegit.StateOne.Service.MSSQL.ReferenceMap;

// Nexus Imports
import com.hooniegit.SourceData.Event.EventWrapper;
import com.hooniegit.SourceData.Tag.TagGroup;
import com.hooniegit.Xtream.Stream.StreamManager;
import com.hooniegit.Xerializer.Serializer.KryoSerializer;

@Service
public class UdpServer {

    private final StreamManager<EventWrapper<TagGroup<Integer>, ConcurrentHashMap<Integer, Integer>>> manager;
    private final ReferenceMap map;
    private static final int PORT = 13002;
    private static final int BUFFER_SIZE = 65507;

    @Autowired
    public UdpServer(StreamManager<EventWrapper<TagGroup<Integer>, ConcurrentHashMap<Integer, Integer>>> manager, ReferenceMap map) {
        this.manager = manager;
        this.map = map;
    }

    @PostConstruct
    private void run() {

        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(PORT);
            byte[] receiveBuffer = new byte[BUFFER_SIZE];

            while (true) {
                // 클라이언트로부터 데이터 수신
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                // 수신 데이터 역직렬화
                EventWrapper<TagGroup<Integer>, ConcurrentHashMap<Integer, Integer>> event = 
                    new EventWrapper<>(KryoSerializer.deserialize(receivePacket.getData()), map.getReference());

                // 이벤트 발행
                this.manager.getNextStream().publishInitialEvent(event);
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
