package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.UserService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/admin/add/users")
    public Result AddUser(@RequestParam("file") MultipartFile file) throws IOException {
        return userService.AddUser(file);
    }

}
