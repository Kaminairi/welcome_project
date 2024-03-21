package com.laughbro.welcome.vo.params.user_params;

import lombok.Data;

import java.util.Date;

@Data
public class UserEditParams {
    private String id;
    private String class_id;
    private String name;
    private String pwd;
    private String sex;
    private String idcard;
    private String nativePlace;
    private String studentOrigin;
    private Date birthday;
    private String tel;
    private String email;
    //
    private String realname;
    private String img;
}
