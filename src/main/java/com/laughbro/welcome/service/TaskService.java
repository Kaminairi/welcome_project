package com.laughbro.welcome.service;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.User;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskParams;

import java.util.List;

public interface TaskService {
    /**
     * 【调用接口】 quest/main
     * 【作用】 获取主线任务
     */
    Result GetMainTask();

    /**
     * 【调用接口】 quest/nmain
     * 【作用】 获取支线任务
     */
    Result GetNmainTask();

    /**
     * 【调用接口】 quest/main
     * 【作用】 通过id获取已完成任务
     */
    Result GetFinishTask(TaskParams taskParams);
}
