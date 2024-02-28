package com.laughbro.welcome.controller;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.utils.Jwt;
import com.laughbro.welcome.vo.params.login_params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录方法
     * @param
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }

}
