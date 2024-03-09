package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.BagService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BagController {
    @Autowired
    private BagService bagService;

    /**
     * 登录方法1  通过账号和密码来登录
     * @param
     * @return
     */
    @GetMapping("/bag_viewall")
    public Result bag_viewall(String userid){
        return bagService.bag_viewall(userid);
    }




}
