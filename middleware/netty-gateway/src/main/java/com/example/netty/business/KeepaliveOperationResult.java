package com.example.netty.business;

import com.example.netty.msg.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {
    private final long time;
}
