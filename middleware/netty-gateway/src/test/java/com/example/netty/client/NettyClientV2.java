/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.example.netty.client;

import com.example.netty.business.AuthOperation;
import com.example.netty.business.KeepaliveOperation;
import com.example.netty.client.codec.*;
import com.example.netty.msg.MsgHeader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the server.  Simply put, the echo client initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public final class NettyClientV2 {

    public static void main(String[] args) throws Exception {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(loggingHandler);
                     p.addLast("frameDecoder", new ClientFrameDecoder());
                     p.addLast("frameEncoder", new ClientFrameEncoder());
                     p.addLast("orderDecoder", new ClientProtocolDecoder());
                     p.addLast("orderEncoder", new ClientProtocolEncoder());
                     // 可以直接write operation，在encoder拼接header
                     p.addLast("operationEncoder", new ClientOperationEncoder());

                 }
             });

            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", 3301).sync();
            MsgHeader header = new MsgHeader(1, 1);
            AuthOperation authOperation = new AuthOperation("admin", "admin");
            RequestMessage requestMessage = new RequestMessage(header, authOperation);
            f.channel().writeAndFlush(requestMessage);

            MsgHeader header2 = new MsgHeader(1, 2);
            KeepaliveOperation keepaliveOperation = new KeepaliveOperation();
            RequestMessage msg2 = new RequestMessage(header2, keepaliveOperation);
            f.channel().writeAndFlush(msg2);

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
