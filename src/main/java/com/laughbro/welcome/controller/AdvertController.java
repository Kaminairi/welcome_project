package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.AdvertService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.advert_params.AddAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.DeleteAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.EditAdvertParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertController {
    @Autowired
    private AdvertService advertService;
    /**
     * 【作用】 管理员发布广告
     */
    @RequestMapping("/admin/post/advert")
    public Result AddAdvert(@RequestBody AddAdvertParams params){
        return advertService.AddAdvert(params);
    }
    /**
     * 【作用】 管理员删除广告
     */
    @RequestMapping("/admin/delete/advert")
    public Result DeleteAdvert(@RequestBody DeleteAdvertParams params){
        return advertService.DeleteAdvert(params);
    }
    /**
     * 【作用】 管理员修改广告
     */
    @RequestMapping("/admin/edit/advert")
    public Result EditAdvert(@RequestBody EditAdvertParams params){
        return advertService.EditAdvert(params);
    }
    /**
     * 【作用】 管理员搜索广告
     */
    @RequestMapping("/admin/get/advert")
    public Result GetAdvertByKeyword(String keyword){
        return advertService.GetAdvertByKeyword(keyword);
    }
    /**
     * 【作用】 管理员查看全部广告
     */
    @RequestMapping("/admin/get/advertall")
    public Result GetAdvertAll(){
        return advertService.GetAdvertAll();
    }
}
