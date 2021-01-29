package com.thatday.user.config;

import cn.hutool.core.util.StrUtil;
import com.thatday.common.token.TokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@ServerEndpoint(value = "/websocket/{token}")
@Component
public class WebSocketServer {

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    //线程安全
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

    private Session session;
    private String userId;

    private static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    private static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) throws IOException {
        this.session = session;
        try {
            this.userId = TokenUtil.getUserInfo(token).getUserId();
        } catch (Exception e) {
            sendMessage("连接失败:" + e.getMessage());
            session.close();
            this.session = null;
            return;
        }

        webSocketSet.remove(userId);
        webSocketSet.put(userId, this);

        addOnlineCount();
        sendMessage("连接成功");

        log.info("有新连接加入！当前在线人数为 " + getOnlineCount() + "--" + userId);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("来自客户端的消息：" + message);
        sendMessage("来自服务端的消息：" + message);
    }

    private void sendMessage(String message) {
        if (session == null) {
            return;
        }
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        if (StrUtil.isEmpty(userId)) {
            log.info("有一连接关闭！当前在线人数为 " + getOnlineCount());
            return;
        }
        webSocketSet.remove(userId);
        subOnlineCount();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    private int getOnlineCount() {
        return onlineCount.get();
    }
}
