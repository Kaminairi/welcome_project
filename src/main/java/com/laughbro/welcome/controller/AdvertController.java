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

    @RequestMapping("/admin/post/advert")
    public Result AddAdvert(@RequestBody AddAdvertParams params){
        return advertService.AddAdvert(params);
    }

    @RequestMapping("/admin/delete/advert")
    public Result DeleteAdvert(@RequestBody DeleteAdvertParams params){
        return advertService.DeleteAdvert(params);
    }

    @RequestMapping("/admin/edit/advert")
    public Result EditAdvert(@RequestBody EditAdvertParams params){
        return advertService.EditAdvert(params);
    }

    @RequestMapping("/admin/get/advert")
    public Result GetAdvertByKeyword(String keyword){
        return advertService.GetAdvertByKeyword(keyword);
    }

    @RequestMapping("/admin/get/advertall")
    public Result GetAdvertAll(){
        return advertService.GetAdvertAll();
    }
}
