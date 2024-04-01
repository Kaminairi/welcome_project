package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.MeService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.me_params.MeChangePwdParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {
    @Autowired
    private MeService meService;
    /**
     * 【作用】查看个人详细信息
     */
    @GetMapping("/me/check/information")
    public Result CheckInformation(String userid){
        return meService.CheckInformation(userid);
    }
    /**
     * 【作用】修改昵称
     */
    @PatchMapping("/me/change/name")
    public Result Changename(@RequestBody MeChangeNameParams params){
        return meService.ChangeName(params);
    }
    /**
     * 【作用】修改头像
     */
    @PatchMapping("/me/change/img")
    public Result ChangeImg(@RequestBody MeChangeImgParams params){
        return meService.ChangeImg(params);
    }
    /**
     * 【作用】修改密码
     */
    @PatchMapping("/me/change/pwd")
    public Result ChangePwd(@RequestBody MeChangePwdParams params){
        return meService.ChangePwd(params);
    }


}
