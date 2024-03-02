package com.laughbro.welcome.dao.pojo;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigInteger;
import java.sql.Date;

@Data
public class Task {
    private BigInteger TaskId;
    private BigInteger SetId;
    private BigInteger LocId;
    private String TaskDemand;
    private DateTime TaskCtime;
    private DateTime TaskBtime;
    private DateTime TaskDtime;
    private int IsMainline;
    private String TaskType;

}
