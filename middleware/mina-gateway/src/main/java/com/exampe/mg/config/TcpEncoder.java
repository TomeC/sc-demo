package com.exampe.mg.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 编码器
 */
@Slf4j
public class TcpEncoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) {
        if(message instanceof TcpByteMessage) {
            encodeByteMsg((TcpByteMessage) message, out);
        }else {
            log.info("不支持的消息");
            log.error("不支持的消息");
        }
    }

    private void encodeByteMsg(TcpByteMessage message, ProtocolEncoderOutput out) {
        byte[] bytes = message.toByteArray();
        IoBuffer buf = IoBuffer.allocate(bytes.length);
        buf.put(bytes);
        buf.flip();
        out.write(buf);
    }

    @Override
    public void dispose(IoSession session) {
    }
}
