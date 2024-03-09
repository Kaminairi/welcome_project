package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.sql.Date;
@Data
public class Task {
    private Integer id;
    private String title;
    private Integer SetId;
    private Integer LocId;
    private String demand;
    private Date ctime;
    private Date btime;
    private Date dtime;
    private Integer IsMainline;
    private String type;

}
