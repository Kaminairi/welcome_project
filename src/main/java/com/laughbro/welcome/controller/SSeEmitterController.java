package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.SSEService;
import com.laughbro.welcome.vo.Result;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
public class SSeEmitterController {
    @Autowired
    SSEService sseService;

    @GetMapping ("/startsse/{id}")
    public SseEmitter startsse(@PathVariable("id") String id){

        //----------形成sseemitter---------------------------------------------------------------
        //塞入emitter
        SseEmitter emitter =new SseEmitter();
        sseService.addSseEmitter(id,emitter);
        return emitter;
        //判断是否成功注入
        /*
        {
            try {
                if(sseService.existEmitter(id)){
                    return emitter;
                }else{

                    return null;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


         */



    }

}
