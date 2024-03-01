package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.sql.Date;
@Data
public class Task {
    private Integer task_id;
    private Integer set_id;
    private Integer loc_id;
    private String task_demand;
    private Date task_ctime;
    private Date task_btime;
    private Date task_dtime;
    private Integer is_mainline;
    private String task_type;

}
