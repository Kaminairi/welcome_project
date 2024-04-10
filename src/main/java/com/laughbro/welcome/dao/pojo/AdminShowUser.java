package com.laughbro.welcome.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class AdminShowUser {
    private String id;
    private String classId;
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
    private String grade;
    private String campus;
    private String college;
    private String speciality;
    @TableField("lass")
    private String classid;
}
