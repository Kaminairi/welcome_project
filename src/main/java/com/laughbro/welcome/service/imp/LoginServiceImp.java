package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.UserMapper;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private UserMapper userMapper;



    /**
     * 失败的返回
     *
     * @param loginParams login接口的输入值，包含用户id和密码
     * @return 包含自定义code和msg以及data的json
     */
    @Override
    public Result login(LoginParams loginParams){
        User user=userMapper.login_select_user_exist_by_id_pwd(loginParams.getId(),loginParams.getPwd());
        //首次登录没有盐值，并且是固定密码，登录后获得一个盐值，然后更改密码
        //1 通过id查询获得账号,盐值和加密密码
        //2 如果返回成功，存在id 那么给输入值加密 然后比较密码
        return Result.success(null);
    }
}
