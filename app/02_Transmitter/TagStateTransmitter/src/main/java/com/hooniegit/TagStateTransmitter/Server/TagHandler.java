package com.hooniegit.TagStateTransmitter.Server;

import com.hooniegit.NettyDataProtocol.Tools.DefaultHandler;
import com.hooniegit.SourceData.Interface.TagData;
import com.hooniegit.TagStateTransmitter.MSSQL.StateService;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 *
 */
public class TagHandler extends DefaultHandler<TagData<Boolean>> {

    private final StateService service;

    public TagHandler(StateService service) {
        this.service = service;
    }
    /**
     *
     * @param ctx
     * @param msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, List<TagData<Boolean>> msg) {
        System.out.println("Received " + msg.size() + " tags");
        service.check(msg);
    }

}
