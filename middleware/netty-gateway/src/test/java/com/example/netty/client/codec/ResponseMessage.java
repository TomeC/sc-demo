package com.example.netty.client.codec;

import com.alibaba.fastjson.JSONObject;
import com.example.netty.msg.MsgHeader;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.StandardCharsets;

@Data
public class ResponseMessage  {
    private MsgHeader header;
    private Object data;

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(header.getVersion());
        byteBuf.writeInt(header.getCmdId());
        byteBuf.writeBytes(JSONObject.toJSONString(data).getBytes());
    }
    public void decode(ByteBuf byteBuf) {
        header = new MsgHeader();
        header.setVersion(byteBuf.readInt());
        header.setCmdId(byteBuf.readInt());
        data = byteBuf.toString(StandardCharsets.UTF_8);
    }
}
