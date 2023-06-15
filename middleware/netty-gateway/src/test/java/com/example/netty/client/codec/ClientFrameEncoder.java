package com.example.netty.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

public class ClientFrameEncoder extends LengthFieldPrepender {
    public ClientFrameEncoder() {
        super(2 );
    }
}
