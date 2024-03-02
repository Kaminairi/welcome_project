package com.laughbro.welcome.service;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskParams;

import java.util.List;

public interface TaskService {

    Result GetMainTask();

    Result GetNmainTask();

    Result GetFinishTask(TaskParams taskParams);
}
