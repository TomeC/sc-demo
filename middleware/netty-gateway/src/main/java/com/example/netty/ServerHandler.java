/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.example.netty;

import com.alibaba.fastjson.JSONObject;
import com.example.netty.business.OperationCmd;
import com.example.netty.msg.MyOperation;
import com.example.netty.msg.OperationResult;
import com.example.netty.msg.RequestMessage;
import com.example.netty.msg.ResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler implementation for the echo server.
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<RequestMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        log.info("request msg: {}", JSONObject.toJSONString(msg));

        OperationCmd opCmd = OperationCmd.fromOpCode(msg.getHeader().getCmdId());
        MyOperation operation = JSONObject.parseObject(msg.getBody(), opCmd.getOpClazz());
        OperationResult result = operation.execute();

        ResponseMessage res = new ResponseMessage();
        res.setHeader(msg.getHeader());
        res.setData(result);
        log.info("response msg: {}", JSONObject.toJSONString(res));

        ctx.writeAndFlush(res);
    }
}
