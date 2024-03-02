package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImp implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    /**
     * 【调用接口】 quest/main
     * 【作用】 获取主线任务
     */
    @Override
    public Result GetMainTask(){
        return Result.success(taskMapper.select_task_main());
    }
    /**
     * 【调用接口】 quest/main
     * 【作用】 获取支线任务
     */
    @Override
    public Result GetNmainTask(){
        return Result.success(taskMapper.select_task_nmain());
    }
    /**
     * 【调用接口】 quest/main
     * 【作用】 通过id获取已完成
     */
    @Override
    public Result GetFinishTask(TaskParams taskParams){
        return Result.success(taskMapper.select_task_finish(taskParams.getId()));
    }

}
