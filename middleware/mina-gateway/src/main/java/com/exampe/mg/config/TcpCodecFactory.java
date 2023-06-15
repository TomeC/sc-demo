package com.exampe.mg.config;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class TcpCodecFactory implements ProtocolCodecFactory {

    private final TcpEncoder encoder;
    private final TcpDecoder decoder;

    public TcpCodecFactory() {
        this.encoder = new TcpEncoder();
        this.decoder = new TcpDecoder();
    }

    public ProtocolDecoder getDecoder(IoSession arg0) {
        return decoder;
    }
    public ProtocolEncoder getEncoder(IoSession arg0) {
        return encoder;
    }
}