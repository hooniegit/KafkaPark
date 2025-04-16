package com.hooniegit.KafkaConsumer.Netty;

import com.hooniegit.NettyDataProtocol.Tools.Decoder;
import com.hooniegit.NettyDataProtocol.Tools.Encoder;

import com.hooniegit.SourceData.Interface.TagData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class NettyChannelClient {

    // [tag] Client Initialization
    private static final int CHANNEL_COUNT_tag = 16;
    private static final String HOST_tag = "localhost";
    private static final int PORT_tag = 13001;

    private final List<Channel> channels_tag = new ArrayList<>(CHANNEL_COUNT_tag);
    private final NioEventLoopGroup group_tag = new NioEventLoopGroup();
    private final AtomicInteger index_tag = new AtomicInteger(0);

    // [mode] Client Initialization
    private static final int CHANNEL_COUNT_mode = 16;
    private static final String HOST_mode = "localhost";
    private static final int PORT_mode = 13002;

    private final List<Channel> channels_mode = new ArrayList<>(CHANNEL_COUNT_mode);
    private final NioEventLoopGroup group_mode = new NioEventLoopGroup();
    private final AtomicInteger index_mode = new AtomicInteger(0);

    // [statusOne] Client Initialization
    private static final int CHANNEL_COUNT_statusOne = 16;
    private static final String HOST_statusOne = "localhost";
    private static final int PORT_statusOne = 13003;

    // [statusOne] Client Initialization
    private final List<Channel> channels_statusOne = new ArrayList<>(CHANNEL_COUNT_statusOne);
    private final NioEventLoopGroup group_statusOne = new NioEventLoopGroup();
    private final AtomicInteger index_statusOne = new AtomicInteger(0);

    // [statusTwo] Client Initialization
    private static final int CHANNEL_COUNT_statusTwo = 16;
    private static final String HOST_statusTwo = "localhost";
    private static final int PORT_statusTwo = 13004;

    private final List<Channel> channels_statusTwo = new ArrayList<>(CHANNEL_COUNT_statusTwo);
    private final NioEventLoopGroup group_statusTwo = new NioEventLoopGroup();
    private final AtomicInteger index_statusTwo = new AtomicInteger(0);

    private static final int CHANNEL_COUNT_statusThree = 16;
    private static final String HOST_statusThree = "localhost";
    private static final int PORT_statusThree = 13005;

    private final List<Channel> channels_statusThree = new ArrayList<>(CHANNEL_COUNT_statusThree);
    private final NioEventLoopGroup group_statusThree = new NioEventLoopGroup();
    private final AtomicInteger index_statusThree = new AtomicInteger(0);

    private volatile boolean initialized = false;

    public synchronized void init_tag() {
        if (initialized) return;

        // [tag] Client Initialization
        Bootstrap bootstrap_tag = new Bootstrap()
                .group(group_tag)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                new Encoder<TagData<Double>>(),
                                new Decoder(),
                                new ChannelInboundHandlerAdapter()
                        );
                    }
                });

        try {
            for (int i = 0; i < CHANNEL_COUNT_tag; i++) {
                Channel channel = bootstrap_tag.connect(HOST_tag, PORT_tag).sync().channel();
                channels_tag.add(channel);
            }
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize channels", e);
        }
    }

    public synchronized void init_mode() {
        if (initialized) return;

        // [mode] Client Initialization
        Bootstrap bootstrap_mode = new Bootstrap()
                .group(group_mode)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                new Encoder<TagData<Double>>(),
                                new Decoder(),
                                new ChannelInboundHandlerAdapter()
                        );
                    }
                });

        try {
            for (int i = 0; i < CHANNEL_COUNT_mode; i++) {
                Channel channel = bootstrap_mode.connect(HOST_mode, PORT_mode).sync().channel();
                channels_mode.add(channel);
            }
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize channels", e);
        }

    }

    public synchronized void init_statusOne() {
        if (initialized) return;

        // [statusOne] Client Initialization
        Bootstrap bootstrap_statusOne = new Bootstrap()
                .group(group_statusOne)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                new Encoder<TagData<Double>>(),
                                new Decoder(),
                                new ChannelInboundHandlerAdapter()
                        );
                    }
                });

        try {
            for (int i = 0; i < CHANNEL_COUNT_statusOne; i++) {
                Channel channel = bootstrap_statusOne.connect(HOST_statusOne, PORT_statusOne).sync().channel();
                channels_statusOne.add(channel);
            }
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize channels", e);
        }

    }

    public synchronized void init_statusTwo() {
        if (initialized) return;

        // [statusTwo] Client Initialization
        Bootstrap bootstrap_statusTwo = new Bootstrap()
                .group(group_statusTwo)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                new Encoder<TagData<Double>>(),
                                new Decoder(),
                                new ChannelInboundHandlerAdapter()
                        );
                    }
                });

        try {
            for (int i = 0; i < CHANNEL_COUNT_statusTwo; i++) {
                Channel channel = bootstrap_statusTwo.connect(HOST_statusTwo, PORT_statusTwo).sync().channel();
                channels_statusTwo.add(channel);
            }
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize channels", e);
        }}

    public synchronized void init_statusThree() {
        if (initialized) return;

        // [statusThree] Client Initialization
        Bootstrap bootstrap = new Bootstrap()
                .group(group_statusThree)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                new Encoder<TagData<Double>>(),
                                new Decoder(),
                                new ChannelInboundHandlerAdapter()
                        );
                    }
                });

        try {
            for (int i = 0; i < CHANNEL_COUNT_statusThree; i++) {
                Channel channel = bootstrap.connect(HOST_statusThree, PORT_statusThree).sync().channel();
                channels_statusThree.add(channel);
            }
            initialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize channels", e);
        }
    }


    public void sendData(List<TagData<Double>> data) {
        if (!initialized) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }

        int channelIndex = index_tag.getAndUpdate(i -> (i + 1) % CHANNEL_COUNT_tag);
        Channel channel = channels_tag.get(channelIndex);

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(data);
        } else {
            System.err.println("Channel " + channelIndex + " is not active.");
        }
    }

    public void sendMode(List<TagData<Boolean>> data) {
        if (!initialized) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }

        int channelIndex = index_mode.getAndUpdate(i -> (i + 1) % CHANNEL_COUNT_mode);
        Channel channel = channels_mode.get(channelIndex);

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(data);
        } else {
            System.err.println("Channel " + channelIndex + " is not active.");
        }
    }

    public void sendStatusOne(List<TagData<Integer>> data) {
        if (!initialized) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }

        int channelIndex = index_statusOne.getAndUpdate(i -> (i + 1) % CHANNEL_COUNT_statusOne);
        Channel channel = channels_statusOne.get(channelIndex);

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(data);
        } else {
            System.err.println("Channel " + channelIndex + " is not active.");
        }
    }

    public void sendStatusTwo(List<TagData<String>> data) {
        if (!initialized) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }

        int channelIndex = index_statusTwo.getAndUpdate(i -> (i + 1) % CHANNEL_COUNT_statusTwo);
        Channel channel = channels_statusTwo.get(channelIndex);

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(data);
        } else {
            System.err.println("Channel " + channelIndex + " is not active.");
        }
    }

    public void sendStatusThree(List<TagData<String>> data) {
        if (!initialized) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }

        int channelIndex = index_statusThree.getAndUpdate(i -> (i + 1) % CHANNEL_COUNT_statusThree);
        Channel channel = channels_statusThree.get(channelIndex);

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(data);
        } else {
            System.err.println("Channel " + channelIndex + " is not active.");
        }
    }

    @PreDestroy
    public void shutdown() {
        for (Channel channel : channels_tag) {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
        }
        group_tag.shutdownGracefully();
        System.out.println("Client shutdown.");
    }


}

