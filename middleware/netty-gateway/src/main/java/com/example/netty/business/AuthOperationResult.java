package com.example.netty.business;

import com.example.netty.msg.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {
    private final boolean passAuth;
}
