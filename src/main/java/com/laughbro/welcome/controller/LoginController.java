package com.laughbro.welcome.controller;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.vo.params.login_params.Login_Idpwd_Params;
import com.laughbro.welcome.vo.params.login_params.Login_Sms_Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录方法
     * @param
     * @return
     */
    @PostMapping("/login_idpwd")
    public Result login_idpwd(@RequestBody Login_Idpwd_Params loginIdpwdParams){
        return loginService.login_idpwd(loginIdpwdParams);
    }

    @PostMapping("/login_sms")
    public Result login_sms(@RequestBody Login_Sms_Params loginSmsParams,HttpSession session){
        return loginService.login_sms(loginSmsParams,session);
    }

    @PostMapping("/sendMsg")
    public Result send_msg(@RequestBody Login_Sms_Params loginSmsParams,HttpSession session) throws Exception {
        loginService.send_msg(loginSmsParams,session);
        return Result.success(1);
    }


}
