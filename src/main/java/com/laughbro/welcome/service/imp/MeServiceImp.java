package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.UserMapper;
import com.laughbro.welcome.service.MeService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.me_params.MeChangePwdParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MeServiceImp implements MeService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result CheckInformation(String userid){
        return Result.success(userMapper.select_user_all_by_id(userid));
    }

    @Override
    public Result ChangeName(MeChangeNameParams params){
        if(params.getName()!=null) {
            userMapper.update_user_name_by_id(params);
            return Result.success("修改成功");
        }else{
            return Result.fail(100,"fail","输入不能为空");
        }
    }
    @Override
    public Result ChangeImg(MeChangeImgParams params){
        userMapper.update_user_img_by_id(params);
        return Result.success("修改成功");
    }
    @Override
    public Result ChangePwd(MeChangePwdParams params){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(params.getPwd());
        userMapper.update_user_pwd_by_id(encodedPassword, params.getUserid());
        return Result.success("修改成功");
    }
}
