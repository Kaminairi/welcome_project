package com.laughbro.welcome.controller;

import com.laughbro.welcome.dao.mapper.BagMapper;
import com.laughbro.welcome.service.BagService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.bag_params.Bag_ViewItem_Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/bag_viewall")
    public Result bag_viewall(@RequestBody Bag_ViewItem_Params bagViewItemParams){
        return bagService.bag_viewall(bagViewItemParams);
    }

}
