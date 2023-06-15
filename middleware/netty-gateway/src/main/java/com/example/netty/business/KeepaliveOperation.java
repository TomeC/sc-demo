package com.example.netty.business;

import com.example.netty.msg.MyOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class KeepaliveOperation extends MyOperation {
    private long time;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    public KeepaliveOperationResult execute() {
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}
