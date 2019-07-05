package com.zyy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid="";

    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        this.sid=sid;
        webSocketSet.add(this);

    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        logger.info("连接关闭！");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自窗口"+sid+"的信息:"+message);

    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        logger.info("推送消息到窗口"+sid+"，推送内容:"+message);
        if (webSocketSet.size() != 0) {
            for (WebSocketServer item : webSocketSet) {
                if(item != null){
                    try {
                        //这里可以设定只推送给这个sid的，为null则全部推送
                        if(sid==null) {
                            item.sendMessage(message);
                        }else if(item.sid.equals(sid)){
                            item.sendMessage(message);
                        }
                    } catch (IOException e) {
                        continue;
                    }
                }

            }
        }

    }



}
