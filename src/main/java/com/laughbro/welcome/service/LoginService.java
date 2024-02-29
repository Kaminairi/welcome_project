package com.laughbro.welcome.service;


import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginParams;

import java.io.Reader;

public interface LoginService {

    Result login(LoginParams loginParams);
}
