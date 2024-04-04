package com.laughbro.welcome.dao.pojo;

import lombok.Data;
import org.joda.time.DateTime;


import java.math.BigInteger;
import java.sql.Date;


@Data
public class Item {

    //item基础信息
    private BigInteger id;
    private String name;
    private String desc;
    private String creator;
    private String ctime;
    private String dtime;
    private int stackLimit;
    private String img;
    private String base64;

    //item_prossession信息，用户持有信息
    private BigInteger PossPk;
    private String UserId;
    private int OwnNum;
    //奖励信息
    private int Rewardnum;

}
