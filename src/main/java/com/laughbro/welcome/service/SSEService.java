package com.laughbro.welcome.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public interface SSEService {


    void addSseEmitter(String userid, SseEmitter emitter);

    void sseSendMessage(String receiceid, Object data) throws IOException;

    boolean existEmitter(String receiceid) throws IOException;

    void deleteEmitter(String receiceid) throws IOException;
}
