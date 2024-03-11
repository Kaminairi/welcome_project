package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import com.laughbro.welcome.vo.params.login_params.LoginSmsParams;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/login/idpwd")
    public Result login_idpwd(String id,String pwd){
        return loginService.login_idpwd(id,pwd);
    }
    /**
     * 登录方法2  通过手机号和验证码来登录
     * @param
     * @return
     */
    @GetMapping("/login/sms")
    public Result login_sms(String tel,String code,HttpSession session){
        return loginService.login_sms(tel,code,session);
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
