package com.example.netty.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ClientFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ClientFrameDecoder() {
        super(2048, 0, 2, 0, 2);
    }
}
