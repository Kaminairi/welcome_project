package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.UserMapper;
import com.laughbro.welcome.service.MeService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.me_params.MeChangePwdParams;
import com.laughbro.welcome.vo.params.me_params.MeCheckInformationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MeServiceImp implements MeService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result CheckInformation(MeCheckInformationParams params){
        return Result.success(userMapper.select_user_all_by_id(params.getUserid()));
    }

    @Override
    public Result ChangeName(MeChangeNameParams params){
        userMapper.update_user_name_by_id(params);
        return Result.success(null);
    }
    @Override
    public Result ChangeImg(MeChangeImgParams params){
        userMapper.update_user_img_by_id(params);
        return Result.success(null);
    }
    @Override
    public Result ChangePwd(MeChangePwdParams params){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("123456");
        userMapper.update_user_pwd_by_id(encodedPassword, params.getUserid());
        return Result.success(null);
    }
}
