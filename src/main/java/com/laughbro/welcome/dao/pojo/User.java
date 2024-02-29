package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;
    private String class_id;
    private String name;
    private String pwd;
    private String sex;
    private String idcard;
    private String native_place;
    private String student_origin;
    private Date birthday;
    private String tel;
    private String email;
    private String salt;

}
