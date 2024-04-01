package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private String name;
    private String img;
    private String id;
    private String creator;
    private String title;
    private String contain;
    private Date ctime;
    private Integer likenum;
    private Integer clicktnum;
    private String TaskId;
    private String iscollect;
}
