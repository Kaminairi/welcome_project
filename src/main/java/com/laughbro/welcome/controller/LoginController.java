package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import com.laughbro.welcome.vo.params.login_params.LoginSmsParams;
import org.apache.ibatis.annotations.Delete;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login/idpwd")
    public Result login_idpwd(@RequestBody LoginIdpwdParams loginIdpwdParams){
        return loginService.login_idpwd(loginIdpwdParams.getId(),loginIdpwdParams.getPwd());
    }
    /**
     * 登录方法2  通过手机号和验证码来登录
     * @param
     * @return
     */
    @PostMapping("/login/sms")
    public Result login_sms(@RequestBody LoginSmsParams loginSmsParams,HttpSession session){
        return loginService.login_sms(loginSmsParams.getTel(),loginSmsParams.getCode(),session);
    }
    /**
     * 登录方法2 匹配的工具接口  通过手机号申请验证码
     * @param
     * @return
     */
    @GetMapping("/login/sendmsg")
    public Result send_msg(String tel, HttpSession session) throws Exception {
        String code=loginService.send_msg(tel,session);
        return Result.success(code);
    }




}
