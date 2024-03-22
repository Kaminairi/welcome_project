package com.laughbro.welcome.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.python.antlr.ast.Str;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket/{id}")
@Slf4j
public class WebSocket {
    // 用于存储WebSocket连接的Map，key为ID，value为对应的Session
    private static Map<String,ConnectionInfo> sessionMap = new ConcurrentHashMap<>();

    // 处理连接建立
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        //查阅一下管理员权限
        String type ="用户";
        //创建用户信息
        ConnectionInfo connectionInfo =new ConnectionInfo(session,id,type);
        TimeUtils timeUtils=new TimeUtils();
        log.info("[{}]------客户端连接---id:---{}---type:---{}", timeUtils.timeGetNow(),id,type );
        sessionMap.put(id, connectionInfo);
        log.info("                     新连接已添加到sessionMap，总数：{}", sessionMap.size());
        log.info("====================================================================");
    }

    // 处理连接关闭
    @OnClose
    public void onClose(Session session, @PathParam("id") String id) {
        TimeUtils timeUtils=new TimeUtils();
        sessionMap.remove(id);
        log.info("[{}]------客户端连接断开：  {} ", id);
        log.info("                     新连接已添加到sessionMap，总数：{}", sessionMap.size());
        log.info("====================================================================");
    }

    // 接收消息
    //请求类型有：消息的转送
    //
    // 接收 JSON 消息并解析
    @OnMessage
    public void onMessage(String message) throws DecodeException {


        // 解析 JSON 消息
        JSONObject json = JSONObject.parseObject(message);
        String type = json.getString("type");//判断请求类型
        String id = json.getString("id");
        String mesg = json.getString("mesg");
        sendMessageToId(id, mesg);

        log.info("收到客户端消息");
    }


    // 发送消息给指定ID的WebSocket连接
    public void sendMessageToId(String id, String message) {
        Session session = sessionMap.get(id).getSession();
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
                log.info("向ID为 {} 的WebSocket连接发送消息：{}", id, message);
            } catch (IOException e) {
                log.error("发送消息失败：{}", e.getMessage());
            }
        } else {
            log.error("ID为 {} 的WebSocket连接不存在", id);
        }
    }




    private static class ConnectionInfo {
        private Session session;
        private String id;
        // 这里可以添加您需要的其他参数
        private String type;

        public ConnectionInfo(Session session, String id,String type) {
            this.session = session;
            this.id = id;
            this.type =type;
        }

        public Session getSession() {
            return session;
        }

        public String getId() {
            return id;
        }
    }




}
