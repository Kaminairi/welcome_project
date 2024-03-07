package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.MeService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.me_params.MeChangePwdParams;
import com.laughbro.welcome.vo.params.me_params.MeCheckInformationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {
    @Autowired
    private MeService meService;
    /**
     * 【作用】查看个人详细信息
     */
    @PostMapping("/me/check/information")
    public Result CheckInformation(@RequestBody MeCheckInformationParams params){
        return meService.CheckInformation(params);
    }
    /**
     * 【作用】修改昵称
     */
    @PostMapping("/me/change/name")
    public Result Changename(@RequestBody MeChangeNameParams params){
        return meService.ChangeName(params);
    }
    /**
     * 【作用】修改头像
     */
    @PostMapping("/me/change/img")
    public Result ChangeImg(@RequestBody MeChangeImgParams params){
        return meService.ChangeImg(params);
    }
    /**
     * 【作用】修改密码
     */
    @PostMapping("/me/change/pwd")
    public Result ChangePwd(@RequestBody MeChangePwdParams params){
        return meService.ChangePwd(params);
    }

}
