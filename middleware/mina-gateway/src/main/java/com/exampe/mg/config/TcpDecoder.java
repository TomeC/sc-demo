package com.exampe.mg.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * CumulativeProtocolDecoder 最重要的工作就是帮你完成了数据累积(解决粘包问题)
 * <p>
 * doDecode()返回值，影响粘包的处理逻辑:
 * 返回true，那么CumulativeProtocolDecoder会再次调用decoder，并把剩余的数据发下来。
 * 返回false，就不处理剩余的，当有新数据包来的时候把剩余的和新的拼接在一起然后再调用doDecode
 * 简而言之，当你认为读取到的数据已经够解码了，那么就返回true，否则就返回false
 * <p>
 */
@Slf4j
public class TcpDecoder extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
        //标记起读位置
        in.mark();
        in.reset();
        return decodeByte(in, out);
    }

    private boolean decodeByte(IoBuffer in, ProtocolDecoderOutput out) {
        if (in.remaining() < 5) {
            return false;
        }
        byte[] data = new byte[]{in.get(),in.get(),in.get(),in.get()};
        Integer cmdId = new Integer(new String(data));
        StringBuilder builder = new StringBuilder();
        while (true) {
            byte t = in.get();
            if (t == '\n' || t == '\r') {
                TcpByteMessage msg = new TcpByteMessage();
                msg.setData(builder.toString());
                msg.setCmdId(cmdId);
                out.write(msg);

                in.get();
                return in.remaining() > 0;
            } else {
                builder.append(t);
            }
        }
    }
}
