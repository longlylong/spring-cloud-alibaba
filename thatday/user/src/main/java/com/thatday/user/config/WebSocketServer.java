package com.thatday.user.config;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@ServerEndpoint(value = "/websocket/{key}")
//@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //线程安全
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap();

    private Session session;
    private String key;

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("key") String key, Session session) {
        this.session = session;
        this.key = key;

        webSocketSet.remove(key);
        webSocketSet.put(key, this);

        addOnlineCount();
        sendMessage("连接成功");

        log.info("有新连接加入！当前在线人数为" + getOnlineCount() + "--" + key);
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
        webSocketSet.remove(key);
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    private int getOnlineCount() {
        return onlineCount;
    }
}