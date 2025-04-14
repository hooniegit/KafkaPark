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

    private static final int CHANNEL_COUNT = 64;
    private static final String HOST = "localhost";
    private static final int PORT = 9999;

    private final List<Channel> channels = new ArrayList<>(CHANNEL_COUNT);
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private final AtomicInteger index = new AtomicInteger(0);

    private volatile boolean initialized = false;

    /**
     * 명시적으로 초기화 호출 (예: @PostConstruct 혹은 외부에서 수동 호출)
     */
    public synchronized void init() {
        if (initialized) return;

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
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
            for (int i = 0; i < CHANNEL_COUNT; i++) {
                Channel channel = bootstrap.connect(HOST, PORT).sync().channel();
                channels.add(channel);
            }
            initialized = true;
            System.out.println("Netty client initialized with " + CHANNEL_COUNT + " channels.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize channels", e);
        }
    }

    /**
     * 외부에서 호출하여 데이터 전송
     */
    public void sendData(List<TagData<Double>> data) {
        if (!initialized) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }

        // 라운드로빈 방식으로 채널 선택
        int channelIndex = index.getAndUpdate(i -> (i + 1) % CHANNEL_COUNT);
        Channel channel = channels.get(channelIndex);

        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(data);
        } else {
            System.err.println("Channel " + channelIndex + " is not active.");
        }
    }

    @PreDestroy
    public void shutdown() {
        for (Channel channel : channels) {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
        }
        group.shutdownGracefully();
        System.out.println("Client shutdown.");
    }
}

