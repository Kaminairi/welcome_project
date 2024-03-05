package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.UserMapper;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.service.LoginService;
import com.laughbro.welcome.utils.JWTUtils;
import com.laughbro.welcome.utils.SMSUtils;
import com.laughbro.welcome.utils.ValidateCodeUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.login_params.LoginIdpwdParams;
import com.laughbro.welcome.vo.params.login_params.LoginSmsParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private SMSUtils smsUtils;

    @Autowired
    private ValidateCodeUtils validateCodeUtils;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    //初始密码
    private static String INIT_PWD = "123456";

    /**
     * 用于使用账号密码的登录方式
     *
     * @param loginIdpwdParams login接口的输入值，包含用户id和密码
     * @return 包含自定义code和msg以及data的json
     */
    @Override
    public Result login_idpwd(LoginIdpwdParams loginIdpwdParams) {
        User user = userMapper.select_user_all_by_id(loginIdpwdParams.getId());
        //没有此用户
        if (user.equals(null)) {
            return Result.fail(101, "登录请求失败：没有当前用户", null);
        } else {
            //第一次登录,没有加密
            if (user.getPwd().equals(INIT_PWD)) {
                //判断一下是不是输入的初始密码
                if (loginIdpwdParams.getPwd().equals(user.getPwd())) {
                    //生成加密
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String encodedPassword = passwordEncoder.encode("123456");
                    //写入数据库
                    userMapper.update_user_pwd_by_id(encodedPassword, user.getId());
                    //return Result.success(encodedPassword);
                    //形成token
                    String token = jwtUtils.buildToken(user.getId(), user.getName());
                    //塞入head
                    response.addHeader("Authorization", token);
                    return Result.success(user);
                } else {
                    //密码错误
                    return Result.fail(101, "初始密码错误", null);
                }
            } else {
                //不是第一次登录
                //给输入值加密
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String rawPassword = loginIdpwdParams.getPwd();
                String encodedPasswordFromDb = user.getPwd();
                if (passwordEncoder.matches(rawPassword, encodedPasswordFromDb)) {
                    // 密码验证通过
                    //生成token
                    String token = jwtUtils.buildToken(user.getId(), user.getName());
                    System.out.println(token);
                    //塞入head
                    response.addHeader("Authorization", token);
                    return Result.success(user);
                    //return Result.success(token);
                } else {
                    // 密码验证失败
                    return Result.fail(101, "密码错误", null);
                }
            }
        }
        //首次登录没有盐值，并且是固定密码，登录后获得一个盐值，然后更改密码
        //1 通过id查询获得账号,盐值和加密密码
        //2 如果返回成功，存在id 那么给输入值加密 然后比较密码
    }

    /**
     * 用于使用短信的登录方式
     *
     * @param
     * @return
     */
    @Override
    public Result login_sms(LoginSmsParams loginSmsParams, HttpSession session) {
        // 从Session中获取验证码
        session = request.getSession();
        String verificationCode = (String) session.getAttribute(loginSmsParams.getTel());
        if(verificationCode==null){
            return Result.fail(111,"手机号没有验证需求",loginSmsParams);
        }else{
            //如果验证码相同
            if(verificationCode.equals(loginSmsParams.getCode())){
                //调取该用户的信息
                User user=userMapper.select_user_all_by_tel(loginSmsParams.getTel());
                return Result.success(user);
            }else {
                return Result.fail(111, "验证码不正确", null);
            }
        }
    }

    /**
     * 用于发送短信
     * 1400609209685643
     *
     * @param
     * @return
     */
    @Override
    public String send_msg(LoginSmsParams loginSmsParams, HttpSession session) throws Exception {
        //生成随机数
        String code = validateCodeUtils.generateValidateCode(4);
        //发送短信
        smsUtils.sendMsg(code,loginSmsParams.getTel());
        // 将验证码放入Session
        session = request.getSession();

        session.setAttribute(loginSmsParams.getTel(), code);

        return code;
    }


}



