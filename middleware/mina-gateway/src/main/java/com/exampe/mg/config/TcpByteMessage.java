package com.exampe.mg.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class TcpByteMessage {
    /**控制码 1字节*/
    private int cmdId;

    /**数据 n字节*/
    private String data;

    public byte[] toByteArray() {

        StringBuilder builder = new StringBuilder();
        builder.append(cmdId)
                .append(data)
                .append("\n");
        return builder.toString().getBytes();
    }
    public TcpByteMessage toMsgObject(byte[] bytes) {
        TcpByteMessage msg = new TcpByteMessage();
        msg.setCmdId(1);
        msg.setData("response");
        return msg;
    }
}
