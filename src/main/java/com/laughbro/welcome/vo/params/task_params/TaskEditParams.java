package com.laughbro.welcome.vo.params.task_params;

import lombok.Data;

@Data
public class TaskEditParams {
    private String id;
    private String title;
    private Integer setId;
    private String demand;
    private String ctime;
    private String btime;
    private String dtime;
    private Integer isMainline;
    private String location;
    private String examplePic;
    private String isAI;
    private String type;
}
