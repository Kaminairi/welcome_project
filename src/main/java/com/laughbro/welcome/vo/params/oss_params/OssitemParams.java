package com.laughbro.welcome.vo.params.oss_params;

import lombok.Data;

import java.math.BigInteger;
@Data

public class OssitemParams {
    //item基础信息
    private String id;
    private String name;
    private String desc;
    private String creator;
    private String ctime;
    private String dtime;
    private int stackLimit;
    private String img;
    private String base64;
}
