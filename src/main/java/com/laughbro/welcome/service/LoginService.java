package com.laughbro.welcome.service;


import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.Login_Idpwd_Params;
import com.laughbro.welcome.vo.params.login_params.Login_Sms_Params;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

public interface LoginService {

    Result login_idpwd(Login_Idpwd_Params loginIdpwdParams);

    Result login_sms(Login_Sms_Params loginSmsParams,HttpSession session);


    int send_msg(Login_Sms_Params loginSmsParams, HttpSession session) throws Exception;
}
