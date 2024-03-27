package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.sql.Date;
@Data
public class Task {
    private Integer id;
    private String title;
    private Integer SetId;
    private String demand;
    private String ctime;
    private String btime;
    private String dtime;
    private Integer IsMainline;
    private String type;
    private String location;
    private String lngLat;
    private String name;
    private String img;
    private String examplePic;
    private String isAI;

    private String isfinish;
}
