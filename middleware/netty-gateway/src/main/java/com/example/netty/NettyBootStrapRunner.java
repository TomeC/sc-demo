package com.example.netty;

import com.example.netty.codec.OrderFrameDecoder;
import com.example.netty.codec.OrderFrameEncoder;
import com.example.netty.codec.OrderProtocolDecoder;
import com.example.netty.codec.OrderProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class NettyBootStrapRunner implements ApplicationRunner {
    @Resource
    private ServerHandler serverHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() ->{
            EventLoopGroup bossGrop = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
            EventLoopGroup workerGroup = new NioEventLoopGroup(10, new DefaultThreadFactory("worker"));
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                LoggingHandler debugLogHandler = new LoggingHandler(LogLevel.DEBUG);
                bootstrap.group(bossGrop, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 200)
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                p.addLast("log", debugLogHandler);
                                p.addLast("idleStateHandler",new IdleStateHandler(120,0,0));
                                p.addLast("myHandler", new MyReadIdleHandler());

                                p.addLast("frameDecoder", new OrderFrameDecoder());
                                p.addLast("frameEncoder", new OrderFrameEncoder());
                                p.addLast("protocolDecoder", new OrderProtocolDecoder());
                                p.addLast("protocolEncoder", new OrderProtocolEncoder());
                                p.addLast(serverHandler);
                            }
                        });
                ChannelFuture f = bootstrap.bind(3301).sync();
                log.info("netty server start success");
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                log.info("netty server failed");
            } finally {
                bossGrop.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();

    }
}
