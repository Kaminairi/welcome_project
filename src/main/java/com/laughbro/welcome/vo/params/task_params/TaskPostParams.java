package com.laughbro.welcome.vo.params.task_params;

import com.laughbro.welcome.dao.pojo.TaskReward;
import lombok.Data;

@Data
public class TaskPostParams {
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
    private TaskReward[] reward;
}
