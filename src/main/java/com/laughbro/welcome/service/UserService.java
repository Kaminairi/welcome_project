package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.user_params.UserDeleteParams;
import com.laughbro.welcome.vo.params.user_params.UserEditParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    Result AddUser(MultipartFile file) throws IOException;

    Result DeleteUser(UserDeleteParams params);

    Result GetUser(String userid);

    Result EditUser(UserEditParams params);
}
