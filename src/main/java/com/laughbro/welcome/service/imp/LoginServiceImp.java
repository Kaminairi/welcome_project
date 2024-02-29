package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.UserMapper;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.utils.JWTUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private HttpServletResponse response;

    //初始密码
    private static String INIT_PWD ="123456";

    /**
     * 失败的返回
     *
     * @param loginParams login接口的输入值，包含用户id和密码
     * @return 包含自定义code和msg以及data的json
     */
    @Override
    public Result login_idpwd(LoginParams loginParams){
        User user=userMapper.select_user_all_by_id(loginParams.getId());
        //没有此用户
        if(user.equals(null)){
            return Result.fail(101,"登录请求失败：没有当前用户",null);
        }else{
            //第一次登录,没有加密
            if(user.getPwd().equals(INIT_PWD)){
                //判断一下是不是输入的初始密码
                if(loginParams.getPwd().equals(user.getPwd())){
                    //生成加密
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String encodedPassword = passwordEncoder.encode("123456");
                    //写入数据库
                    userMapper.update_user_pwd_by_id(encodedPassword,user.getId());
                    //return Result.success(encodedPassword);
                    //形成token
                    String token =jwtUtils.buildToken(user.getId(),user.getName());
                    //塞入head
                    response.addHeader("Authorization", token);
                    return Result.success(user);
                }else{
                    //密码错误
                    return Result.fail(101,"初始密码错误",null);
                }
            }else{
                //不是第一次登录
                //给输入值加密
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String rawPassword = loginParams.getPwd();
                String encodedPasswordFromDb = user.getPwd();
                if (passwordEncoder.matches(rawPassword, encodedPasswordFromDb)) {
                    // 密码验证通过
                    //生成token
                    String token =jwtUtils.buildToken(user.getId(),user.getName());
                    System.out.println(token);
                    //塞入head
                    response.addHeader("Authorization", token);
                    return Result.success(user);
                    //return Result.success(token);
                } else {
                    // 密码验证失败
                    return Result.fail(101,"密码错误",null);
                }
            }
        }



        //首次登录没有盐值，并且是固定密码，登录后获得一个盐值，然后更改密码
        //1 通过id查询获得账号,盐值和加密密码
        //2 如果返回成功，存在id 那么给输入值加密 然后比较密码




    }
}
