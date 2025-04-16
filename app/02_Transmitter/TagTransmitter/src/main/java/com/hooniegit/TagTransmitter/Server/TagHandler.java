package com.hooniegit.TagTransmitter.Server;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import com.hooniegit.TagTransmitter.Service.Transmitter;

// Nexus Import
import com.hooniegit.NettyDataProtocol.Tools.DefaultHandler;
import com.hooniegit.SourceData.Interface.TagData;

/**
 *
 */
public class TagHandler extends DefaultHandler<TagData<Double>> {

    /**
     *
     * @param ctx
     * @param msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, List<TagData<Double>> msg)  {
        System.out.println("Received " + msg.size() + " tags");
    }

    private void transport(List<TagData<Double>> msg, int port) throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            Gson gson = new Gson();
            byte[] data = gson.toJson(new Object[]{msg.get(0).getTimestamp(), msg}).getBytes();

            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("{대상 ip}"), port);
            socket.send(packet);
            socket.close();
            packet = null;

            msg = null;
            gson = null;
            data = null;
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}
