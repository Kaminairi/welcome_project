package com.laughbro.welcome.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.laughbro.welcome.dao.mapper.BagMapper;
import com.laughbro.welcome.dao.pojo.Item;
import com.laughbro.welcome.dao.pojo.Post;
import com.laughbro.welcome.service.BagService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BagController {
    @Autowired
    private BagService bagService;
    @Autowired
    private BagMapper bagMapper;

    /**
     * 登录方法1  通过账号和密码来登录
     * @param
     * @return
     */
    @GetMapping("/bag_viewall")
    public Result bag_viewall(String userid){
        return bagService.bag_viewall(userid);
    }


    @GetMapping("Manger/getitemlist")
    public Result Me_get_items(int page,int pagesize){
        PageHelper.startPage(page, pagesize-2);
        Page<Item> p;
        p=(Page<Item>) bagMapper.select_all_items();
        if(p.isEmpty())
            return Result.fail(201,"没有结果了",null);
        return Result.success(p.getResult());
    }



}
