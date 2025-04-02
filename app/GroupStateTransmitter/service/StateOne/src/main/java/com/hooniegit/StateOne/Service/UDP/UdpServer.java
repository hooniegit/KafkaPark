package com.hooniegit.StateOne.Service.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

// Nexus Imports
import com.hooniegit.SourceData.Tag.TagGroup;
import com.hooniegit.StateOne.Service.MSSQL.MssqlService;
import com.hooniegit.Xtream.Stream.StreamManager;
import com.hooniegit.Xerializer.Serializer.KryoSerializer;

@Service
public class UdpServer {

    private final StreamManager<TagGroup<Integer>> manager;
    private final MssqlService service;
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 65507;

    @Autowired
    public UdpServer(StreamManager<TagGroup<Integer>> manager, MssqlService service) {
        this.manager = manager;
        this.service = service;
    }

    @PostConstruct
    private void run() {

        DatagramSocket serverSocket = null;

        try {

            serverSocket = new DatagramSocket(PORT);
            // System.out.println("UDP 서버가 포트 " + PORT + "에서 시작되었습니다.");

            byte[] receiveBuffer = new byte[BUFFER_SIZE];

            while (true) {
                // 클라이언트로부터 데이터 수신
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                // 수신 데이터 역직렬화
                TagGroup<Integer> tagGroup = KryoSerializer.deserialize(receivePacket.getData());

                // 이벤트 발행
                this.manager.getNextStream().publishInitialEvent(tagGroup);
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
