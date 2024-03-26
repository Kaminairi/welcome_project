package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.UserService;
import com.laughbro.welcome.vo.PageResult;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.user_params.UserDeleteParams;
import com.laughbro.welcome.vo.params.user_params.UserEditParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping("/admin/delete/user")
    public Result DeleteUser(@RequestBody UserDeleteParams params){
        return userService.DeleteUser(params);
    }

    @RequestMapping("/admin/get/user")
    public Result GetUser(String userid){
        return userService.GetUser(userid);
    }

    @RequestMapping("/admin/edit/user")
    public Result EditUser(@RequestBody UserEditParams params){
        return userService.EditUser(params);
    }

    @RequestMapping("/admin/get/userall")
    public PageResult GetUserAll(int page, int pagesize,int order){
        return userService.GetUserAll(page,pagesize,order);
    }
}
