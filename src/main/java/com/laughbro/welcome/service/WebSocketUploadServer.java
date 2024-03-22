package com.laughbro.welcome.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.OnMessage;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/*
@Slf4j
public class WebSocketUploadServer {


    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        //前端传过来的消息都是一个json
        JSONObject jsonObject = JSON.parseObject(message);
        //消息类型
        String type = jsonObject.getString("type");
        //消息内容
        String data = jsonObject.getString("data");
        //判断类型是否为文件名
        if ("fileName".equals(type)) {
            log.info("传输文件为:" + data);
            //此处的 “.”需要进行转义
           // String[] split = data.split("\\.");
                      try {
                Map<String, Object> map = saveFileI.docPath(data);
                docUrl = (HashMap) map;
                this.sendMessage("ok");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if ("fileCount".equals(type)){
            log.info("传输第"+data+"份");
        }
        //判断是否结束
        else if (endupload.equals(type)) {
            LOG.info("===============>传输成功");
            //返回一个文件下载地址
            String path = (String) docUrl.get("nginxPath");
            //返回客户端文件地址
            try {
                this.sendMessage(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }










}


        */