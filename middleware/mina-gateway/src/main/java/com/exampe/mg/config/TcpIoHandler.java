package com.exampe.mg.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.MDC;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
public class TcpIoHandler extends IoHandlerAdapter {
    @Resource
    private TcpSessionMap tcpSessionMap;

    //第一步
    @Override
    public void sessionCreated(IoSession session) {
        log.info("session created remoteAddress = {},sessionId = {}", session.getRemoteAddress(), session.getId());
        log.info("remote client [" + session.getRemoteAddress().toString() + "] connected.");
    }
    // 第二步
    @Override
    public void sessionOpened(IoSession session) {
        log.info("sessionOpened");
        session.getConfig().setBothIdleTime(3*60);
    }



    @Override
    public void messageReceived(IoSession session, Object message) {
        log.info("messageReceived");
        try {
            //处理接收到的数据
            processMessage(session, message);
        } catch (Exception e) {
            log.error("session收发数据错误", e);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        log.info("messageSent");
        if (message instanceof TcpByteMessage) {
            log.info("Sent: \n {}", message.toString());
        }
    }




    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        log.info("session idle remoteAddress = {},sessionId = {}", session.getRemoteAddress(), session.getId());
        String sessionId = session.getAttribute(SessionKey.SESSION_ID) == null ? null : (String) session.getAttribute(SessionKey.SESSION_ID);
        if (null != sessionId) {
            log.info("清除[{}]Session", sessionId);
            tcpSessionMap.removeSession(sessionId, session);
        }
        session.closeOnFlush();
    }

    @Override
    public void sessionClosed(IoSession session) {
        log.info("session closed remoteAddress = {},sessionId = {}", session.getRemoteAddress(), session.getId());
        String sessionId = session.getAttribute(SessionKey.SESSION_ID) == null ? null : (String) session.getAttribute(SessionKey.SESSION_ID);
        if (null != sessionId) {
            log.info("清除[{}]Session", sessionId);
            tcpSessionMap.removeSession(sessionId, session);
        }
        session.closeOnFlush();
    }


    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.closeOnFlush();
        log.error("session遇到异常，关闭session, " + cause.toString(), cause);
    }

    private void processMessage(IoSession session, Object message) {
        log.info("Recv: \n {}", message.toString());
        if (message instanceof TcpByteMessage) {
            processByteMessage(session, (TcpByteMessage) message);
        } else {
            log.error("非法消息");
        }
    }


    private void processByteMessage(IoSession session, TcpByteMessage msg) {
        String requestId = String.valueOf(UUID.randomUUID());
        MDC.put("requestId",requestId);
        long start = System.currentTimeMillis();

        switch (msg.getCmdId()) {
            //终端签到
            case 1: {
                session.write(msg);
                break;
            }

            default:
                log.info("default:{}",msg);
                // process default msg
                break;
        }
    }

    /**
     * 根据请求消息生成响应消息
     */
    private TcpByteMessage genResponseMsg(TcpByteMessage reqMsg) {
        TcpByteMessage respMsg = new TcpByteMessage();
        respMsg.setCmdId(reqMsg.getCmdId());
        return respMsg;
    }
}

