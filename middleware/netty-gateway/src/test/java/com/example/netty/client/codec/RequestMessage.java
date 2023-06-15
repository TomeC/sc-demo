package com.example.netty.client.codec;

import com.alibaba.fastjson.JSONObject;
import com.example.netty.msg.MsgHeader;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessage {
    private MsgHeader header;
    private Object body;

    public void decode(ByteBuf byteBuf) {
        header = new MsgHeader();
        header.setVersion(byteBuf.readInt());
        header.setCmdId(byteBuf.readInt());
        body = byteBuf.toString(StandardCharsets.UTF_8);
    }

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(header.getVersion());
        byteBuf.writeInt(header.getCmdId());
        byteBuf.writeBytes(JSONObject.toJSONString(body).getBytes());
    }
}
