package com.laughbro.welcome.dao.pojo;

import lombok.Data;

@Data
public class Advert {
    private String id;
    private String name;
    private String img;
    private String url;
    private String clickNum;
    private String content;
    private  int weight;
    //曝光
    private String extime;

}
