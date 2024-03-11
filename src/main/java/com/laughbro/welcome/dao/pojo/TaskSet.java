package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class TaskSet {
    private String SetId;
    private String ClassId;
    private String name;
    private String applicant;
    private String ctime;
    private String IsSetDue;
    private String IsMainline;
    private String etime;
}
