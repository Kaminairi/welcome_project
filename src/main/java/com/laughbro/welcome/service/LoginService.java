package com.laughbro.welcome.service;


import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import com.laughbro.welcome.vo.params.login_params.LoginSmsParams;

import javax.servlet.http.HttpSession;

public interface LoginService {

    Result login_idpwd(LoginIdpwdParams loginIdpwdParams);

    Result login_sms(LoginSmsParams loginSmsParams, HttpSession session);


    int send_msg(LoginSmsParams loginSmsParams, HttpSession session) throws Exception;



}
