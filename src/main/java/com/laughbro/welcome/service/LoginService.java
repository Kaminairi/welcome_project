package com.laughbro.welcome.service;


import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginParams;

public interface LoginService {

    Result login_idpwd(LoginParams loginParams);
}
