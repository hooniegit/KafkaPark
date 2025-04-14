package com.hooniegit.TagTransmitter.Server;

import io.netty.channel.ChannelHandlerContext;

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
    protected void channelRead0(ChannelHandlerContext ctx, List<TagData<Double>> msg) {
        System.out.println("Received " + msg.size() + " tags");

//        Transmitter.udp();

    }

}
