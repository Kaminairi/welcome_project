package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import com.laughbro.welcome.vo.params.login_params.LoginSmsParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录方法1  通过账号和密码来登录
     * @param
     * @return
     */
    @PostMapping("/login_idpwd")
    public Result login_idpwd(@RequestBody LoginIdpwdParams loginIdpwdParams){
        return loginService.login_idpwd(loginIdpwdParams);
    }
    /**
     * 登录方法2  通过手机号和验证码来登录
     * @param
     * @return
     */
    @PostMapping("/login_sms")
    public Result login_sms(@RequestBody LoginSmsParams loginSmsParams, HttpSession session){
        return loginService.login_sms(loginSmsParams,session);
    }
    /**
     * 登录方法2 匹配的工具接口  通过手机号申请验证码
     * @param
     * @return
     */
    @PostMapping("/sendMsg")
    public Result send_msg(@RequestBody LoginSmsParams loginSmsParams, HttpSession session) throws Exception {
        loginService.send_msg(loginSmsParams,session);
        return Result.success(1);
    }





}
