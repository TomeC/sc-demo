package com.example.netty.codec;

import io.netty.handler.codec.LengthFieldPrepender;

public class OrderFrameEncoder extends LengthFieldPrepender {
    public OrderFrameEncoder() {
        super(2 );
    }
}
