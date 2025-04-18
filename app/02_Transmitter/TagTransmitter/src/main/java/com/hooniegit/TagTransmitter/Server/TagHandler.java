package com.hooniegit.TagTransmitter.Server;

import com.google.gson.Gson;

import io.netty.channel.ChannelHandlerContext;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Nexus Import
import com.hooniegit.NettyDataProtocol.Tools.DefaultHandler;
import com.hooniegit.SourceData.Interface.TagData;

/**
 *
 */
public class TagHandler extends DefaultHandler<TagData<Double>> {

    // Server Configuration
    private final String host = "localhost";
    private final int referencePort = 8999;
    private final int devideCount = 3000;

    // Re-Usable Objects
    private final Gson gson = new Gson();
    private DatagramSocket socket;

    // Initialization Flag
    private boolean initialized = false;

    /**
     *
     * @param ctx
     * @param msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, List<TagData<Double>> msg)  {
        try{
            initialize();
            task(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws SocketException
     */
    private void initialize() throws SocketException {
        if (initialized) {
            return;
        }
        try {
            socket = new DatagramSocket();
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param msg
     */
    private void task(List<TagData<Double>> msg) throws Exception{
        for (Map.Entry<Integer, List<TagData<Double>>> entry : groupTagData(msg).entrySet()) {
            List<TagData<Double>> tagDataList = entry.getValue();
            if (tagDataList.size() > 0) {
                transport(tagDataList, this.referencePort + entry.getKey());
            }
        }
    }

    /**
     *
     * @param tagDataList
     * @return
     */
    private Map<Integer, List<TagData<Double>>> groupTagData(List<TagData<Double>> tagDataList) {
        Map<Integer, List<TagData<Double>>> groupedMap = new HashMap<>();

        for (TagData<Double> tagData : tagDataList) {
            int id = tagData.getId();
            if (id < 1 || id > 1_800_000) {
                continue;
            }
            int key = (id - 1) / devideCount + 1;
            groupedMap.computeIfAbsent(key, k -> new ArrayList<>()).add(tagData);
        }

        return groupedMap;
    }

    /**
     *
     * @param msg
     * @param port
     * @throws Exception
     */
    private void transport(List<TagData<Double>> msg, int port) throws Exception {
        byte[] data = gson.toJson(new Object[]{msg.get(0).getTimestamp(), msg}).getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(this.host), port);
        socket.send(packet);
        socket.close();
    }

}
