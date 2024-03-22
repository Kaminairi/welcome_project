package com.laughbro.welcome.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class SSeEmitterController {

    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();
    @GetMapping(value = "/subscribe")
    public SseEmitter subscribe(String id) {
        // 超时时间设置为1小时
        SseEmitter sseEmitter = new SseEmitter(30000L);
        sseCache.put(id, sseEmitter);
        sseEmitter.onCompletion(() -> {
            sseCache.remove(id);
            System.out.println("完成！！！");
        });
        sseEmitter.onError((error) -> {
            sseCache.remove(id);
            error.printStackTrace();
        });
        return sseEmitter;
    }
    @GetMapping(value = "/push")
    public String push(String id, String content) throws IOException {
        SseEmitter sseEmitter = sseCache.get(id);
        if (sseEmitter != null) {
            try {
                SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event().data(content);
                sseEmitter.send(eventBuilder);
                sseEmitter.complete();
            } catch (Exception e) {
                sseCache.remove(id);
                e.printStackTrace();
            }
        }
        return "over";
    }
    @GetMapping(value = "/over")
    public String over(String id) {
        SseEmitter sseEmitter = sseCache.get(id);
        if (sseEmitter != null) {
            sseEmitter.complete();
            sseCache.remove(id);
        }
        return "over";
    }


/*
    private final  Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();


    @GetMapping(path = "subscribe", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter push(String id) throws IOException {
        // 超时时间设置为5分钟，用于演示客户端自动重连
        SseEmitter sseEmitter = new SseEmitter(3000L);
        // 设置前端的重试时间为1s
        // send(): 发送数据，如果传入的是一个非SseEventBuilder对象，那么传递参数会被封装到 data 中
        sseEmitter.send(SseEmitter.event().reconnectTime(1000).data("连接成功"));
        sseCache.put(id, sseEmitter);
        System.out.println("add " + id);
        sseEmitter.send("你好", MediaType.APPLICATION_JSON);
        SseEmitter.SseEventBuilder data = SseEmitter.event().name("finish").id("6666").data("哈哈");
        sseEmitter.send(data);
        // onTimeout(): 超时回调触发
        sseEmitter.onTimeout(() -> {
            System.out.println(id + "超时");
            sseCache.remove(id);
        });
        // onCompletion(): 结束之后的回调触发
        sseEmitter.onCompletion(() -> System.out.println("完成！！！"));
        return sseEmitter;
    }


    @ResponseBody
    @PostMapping(path = "push")
    public String push(String id, String content) throws IOException {
        SseEmitter sseEmitter = sseCache.get(id);
        if (sseEmitter != null) {
            sseEmitter.send(content);
        }
        return "over";
    }

    @ResponseBody
    @GetMapping(path = "over")
    public String over(String id) {
        SseEmitter sseEmitter = sseCache.get(id);
        if (sseEmitter != null) {
            // complete(): 表示执行完毕，会断开连接
            sseEmitter.complete();
            sseCache.remove(id);
        }
        return "over";
    }

    */

}
