package com.example.netty.client.codec;

import com.example.netty.msg.MyOperation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ClientOperationEncoder extends MessageToMessageEncoder<MyOperation> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyOperation myOperation, List<Object> list) throws Exception {
        log.info("MyOperation encode");
    }
}

