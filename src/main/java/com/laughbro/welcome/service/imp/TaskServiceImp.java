package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskParams;
import com.laughbro.welcome.vo.params.task_params.TasksParams;
import com.laughbro.welcome.vo.params.task_params.TaskSetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImp implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Result GetTaskSetMainUnfinish(TaskSetParams taskSetParams){
        return Result.success(taskMapper.select_taskset_main_unfinish(taskSetParams.getUserid(),1,0));
    }

    @Override
    public Result GetTaskSetNmainUnfinish(TaskSetParams taskSetParams){
        return Result.success(taskMapper.select_taskset_nmain_unfinish(taskSetParams.getUserid(),0,0));
    }

    @Override
    public Result GetTasks(TasksParams tasksParams){
        return Result.success(taskMapper.select_task_by_set_id(tasksParams.getTasksetid(),0));
    }

    @Override
    public Result GetTask(TaskParams params){
        return Result.success(taskMapper.select_task_by_id(params.getTaskid()));
    }

}
