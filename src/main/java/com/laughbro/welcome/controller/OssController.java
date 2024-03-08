package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.OssService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager/auth")
public class OssController {
    @Autowired
    private OssService ossService;

    @GetMapping(value = "/oss/token")
    //这里的R是我自己封装的统一返回对象 你也可以直接返回JSON啥的
    public Result ossToken() {
        return Result.success(ossService.token());
    }

}
