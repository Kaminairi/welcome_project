package com.laughbro.welcome.vo.params.task_params;

import lombok.Data;

@Data
public class TaskEditParams {
    private String id;
    private String title;
    private String setId;
    private String demand;
    private String ctime;
    private String btime;
    private String dtime;
    private String isMainline;
    private String location;
    private String examplePic;
    private String isAI;
    private String type;
}
