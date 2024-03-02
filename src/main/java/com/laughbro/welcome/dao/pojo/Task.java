package com.laughbro.welcome.dao.pojo;

import lombok.Data;
@Data
public class Task {
    private Integer id;
    private Integer SetId;
    private Integer loc;
    private String demand;
    private String ctime;
    private String btime;
    private String dtime;
    private Integer IsMainline;
    private String type;

}
