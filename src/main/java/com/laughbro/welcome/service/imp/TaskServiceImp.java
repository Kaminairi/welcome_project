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
    public Result GetTaskSetMainUnfinish(String userid){
        return Result.success(taskMapper.select_taskset_main_unfinish(userid,1,0));
    }

    @Override
    public Result GetTaskSetNmainUnfinish(String userid){
        return Result.success(taskMapper.select_taskset_nmain_unfinish(userid,0,0));
    }

    @Override
    public Result GetTasks(String setid){
        return Result.success(taskMapper.select_task_by_set_id(setid,0));
    }

    @Override
    public Result GetTask(String taskid){
        return Result.success(taskMapper.select_task_by_id(taskid));
    }

}
