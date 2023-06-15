package com.example.netty.business;

import com.example.netty.msg.MyOperation;
import com.example.netty.msg.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum OperationCmd {
    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),

    ;

    private int opCode;
    private Class<? extends MyOperation> opClazz;
    private Class<? extends OperationResult> opResClazz;


    public static OperationCmd fromOpCode(int code) {
        return Stream.of(OperationCmd.values()).filter(item -> item.getOpCode()==code).findFirst().orElseThrow(
                ()->new RuntimeException("not found type")
        );
    }
}
