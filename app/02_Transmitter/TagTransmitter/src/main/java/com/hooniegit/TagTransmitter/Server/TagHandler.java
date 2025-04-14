package com.hooniegit.TagTransmitter.Server;

import com.hooniegit.NettyDataProtocol.Tools.DefaultHandler;
import com.hooniegit.SourceData.Interface.TagData;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class TagHandler extends DefaultHandler<TagData<Double>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, List<TagData<Double>> msg) {
        System.out.println("Received " + msg.size() + " tags");
    }
}
