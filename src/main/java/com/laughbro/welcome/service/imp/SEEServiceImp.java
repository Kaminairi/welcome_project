package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.service.SSEService;
import com.laughbro.welcome.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sseEmitter的实现类
 */
@Service
public class SEEServiceImp implements SSEService {

    private final Map<String, SseEmitter> sseEmitters =new ConcurrentHashMap<>();
    /**
     * 将id对应的emitter1注入map
     */
    @Override
    public void addSseEmitter(String userid, SseEmitter emitter){
        //注入
        sseEmitters.put(userid,emitter);

    }
    /**
     * 发送信息
     */
    @Override
    public void sseSendMessage(String receiceid, Object data) throws IOException {
        SseEmitter emitter =sseEmitters.get(receiceid);
        if(emitter!=null){
            Result r=Result.success(data);
            emitter.send(SseEmitter.event().data(r));
            // emitter.send(SseEmitter.event().data(data));
        }
    }
    /**
     * 检验是否存入改emitter
     */
    @Override
    public boolean existEmitter(String receiceid) throws IOException {
        SseEmitter emitter =sseEmitters.get(receiceid);
        if(emitter!=null){
            return false;
        }
        return true;
    }

    /**
     * 删除emitter
     */
    @Override
    public void deleteEmitter(String receiceid) throws IOException {
        sseEmitters.remove(receiceid);
    }
}
