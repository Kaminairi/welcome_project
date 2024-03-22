package com.laughbro.welcome.utils;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CustomMessageDecoder implements Decoder.Text<JSONObject> {

    @Override
    public JSONObject decode(String s) throws DecodeException {
        // 将字符串 s 解析为 JSON 对象
        return JSON.parseObject(s);
    }

    @Override
    public boolean willDecode(String s) {
        // 这里可以添加一些校验逻辑，判断接收到的消息是否能够被解码
        try {
            JSON.parseObject(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
        // 初始化方法，一般为空实现即可
    }

    @Override
    public void destroy() {
        // 销毁方法，一般为空实现即可
    }
}