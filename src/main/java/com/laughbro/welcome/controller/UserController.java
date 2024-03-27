package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.UserService;
import com.laughbro.welcome.vo.PageResult;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.user_params.UserDeleteParams;
import com.laughbro.welcome.vo.params.user_params.UserEditParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 【作用】excel批量导入用户
     */
    @RequestMapping("/admin/add/users")
    public Result AddUser(@RequestParam("file") MultipartFile file) {
        try {
            return userService.AddUser(file);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(100,"文件处理失败",null);
        }
    }
    /**
     * 【作用】删除用户
     */
    @RequestMapping("/admin/delete/user")
    public Result DeleteUser(@RequestBody UserDeleteParams params){
        return userService.DeleteUser(params);
    }
    /**
     * 【作用】搜索用户
     */
    @RequestMapping("/admin/get/user")
    public Result GetUser(String userid){
        return userService.GetUser(userid);
    }
    /**
     * 【作用】编辑用户信息
     */
    @RequestMapping("/admin/edit/user")
    public Result EditUser(@RequestBody UserEditParams params){
        return userService.EditUser(params);
    }
    /**
     * 【作用】获取所有用户列表
     */
    @RequestMapping("/admin/get/userall")
    public PageResult GetUserAll(int page, int pagesize,int order){
        return userService.GetUserAll(page,pagesize,order);
    }
}
