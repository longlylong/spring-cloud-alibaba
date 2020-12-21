package com.thatday.user.config;

import com.thatday.common.token.TokenUtil;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
//@ServerEndpoint(value = "/websocket/{key}")
//@Component
public class WebSocketServer {

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    //线程安全
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap();

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
    public void onOpen(@PathParam("key") String token, Session session) throws IOException {
        this.session = session;
        try {
            this.userId = TokenUtil.getUserInfo(token).getUserId();
        } catch (Exception e) {
            sendMessage("连接失败:" + e.getMessage());
            session.close();
            return;
        }

        webSocketSet.remove(userId);
        webSocketSet.put(userId, this);

        addOnlineCount();
        sendMessage("连接成功");

        log.info("有新连接加入！当前在线人数为" + getOnlineCount() + "--" + userId);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("来自客户端的消息：" + message);
        sendMessage("来自服务端的消息：" + message);
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(userId);
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
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
