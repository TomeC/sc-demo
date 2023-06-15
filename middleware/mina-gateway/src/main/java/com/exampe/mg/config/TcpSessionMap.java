package com.exampe.mg.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by brian on 2017/7/25.
 */

@Slf4j
@Component
public class TcpSessionMap {
    private ConcurrentHashMap<String, IoSession> map = new ConcurrentHashMap<>();

    /**
     * 保存session会话
     */
    public void addSession(String key, IoSession session) {
//        log.info("添加[{}]连接", key);
        this.map.put(key, session);
    }

    public void removeSession(String key) {
        log.info("清除[{}]连接", key);
        this.map.remove(key);
    }
    // case : 503
    // t0 1 -> session_01
    // t1 session_01 broken but server unaware , server timeout clearing
    // t2 new session_02 -> 1 -> session_02
    // t3 server clear session_01 removeByKey(1) -> session_02 cleared WRONG!
    public void removeSession(String key, IoSession session) {
        log.info("清除[{}]连接", key);
        IoSession sessionCurrent = getSession(key);
        if(sessionCurrent != null && sessionCurrent.getId() != session.getId()){
            log.info("清除[{}]连接,防止误杀,sessionId = {}", key,session.getId());
            return;
        }
        removeSession(key);
    }

    /**
     * 根据key查找缓存的session
     */
    public IoSession getSession(String key) {
        return this.map.get(key);
    }

    /**
     * 判断是否已经存在key
     */
    public boolean hasSession(String key) {
        return this.map.containsKey(key);
    }

    /**
     * 获取当前所有session
     */
    public Map<String, IoSession> getIoSessionCopy() {
        Map<String, IoSession> copyMap = new HashMap<>();
        //遍历值
        for(Map.Entry<String, IoSession> e: map.entrySet() ){
            copyMap.put(e.getKey(), e.getValue());
        }
        return copyMap;
    }
    
}
