package com.laughbro.welcome.service;


import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import com.laughbro.welcome.vo.params.login_params.LoginSmsParams;

import javax.servlet.http.HttpSession;

public interface LoginService {

    Result login_idpwd(String id,String pwd);

    Result login_sms(String tel,String code, HttpSession session);


    String send_msg(String tel, HttpSession session) throws Exception;



}
