package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImp implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Override
    public Result GetTaskSets(String userid,Integer is_mainline,Integer is_now){
        return Result.success(taskMapper.select_tasksets(userid,is_mainline,is_now));
    }

    @Override
    public Result GetTasks(String setid,Integer is_now){
        return Result.success(taskMapper.select_task_by_set_id(setid,is_now));
    }

    @Override
    public Result GetTask(String taskid){
        return Result.success(taskMapper.select_task_by_id(taskid));
    }

    public Result GetTaskAll(String userid){
        return Result.success(taskMapper.select_task_all(userid));
    }
}
