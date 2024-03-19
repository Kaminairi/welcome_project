package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    Result AddUser(MultipartFile file) throws IOException;
}
