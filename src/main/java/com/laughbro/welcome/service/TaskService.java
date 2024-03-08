package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskParams;
import com.laughbro.welcome.vo.params.task_params.TasksParams;
import com.laughbro.welcome.vo.params.task_params.TaskSetParams;

public interface TaskService {

    Result GetTaskSetMainUnfinish(TaskSetParams taskSetParams);


    Result GetTaskSetNmainUnfinish(TaskSetParams taskSetParams);


    Result GetTasks(TasksParams tasksParams);

    Result GetTask(TaskParams params);
}
