package com.laughbro.welcome.controller;


import org.springframework.web.bind.annotation.GetMapping;
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
        SseEmitter sseEmitter = new SseEmitter(0L);
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
                System.out.println("发送！！！");
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
}
