package com.example.netty.client.dispacher;

import com.example.netty.client.codec.ResponseMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {
    private Map<Integer, OperationResultFuture> map = new ConcurrentHashMap<>();


    public void add(Integer cmdId, OperationResultFuture future) {
        this.map.put(cmdId, future);
    }

    public void set(Integer cmdId, ResponseMessage responseMessage) {
        OperationResultFuture operationResultFuture = this.map.get(cmdId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(responseMessage);
            this.map.remove(cmdId);
        }
    }
}
