package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class Post {
    private String name;
    private String id;
    private String creator;
    private String title;
    private String contain;
    private Date ctime;
    private Integer likenum;
    private Integer clicktnum;
    private String TaskId;
    private String iscollect;

    //ad
    private String img;
    private String url;
    private String adid;
    private int is_adpost;
    private String extime;//曝光时间

}
