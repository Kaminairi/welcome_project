package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.SSEService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    SSEService sseService;

    @PostMapping("/testsse")
    public Result testsse(String data) throws IOException {
        sseService.sseSendMessage("1111111111",data);
        return Result.success(null);
    }
}
