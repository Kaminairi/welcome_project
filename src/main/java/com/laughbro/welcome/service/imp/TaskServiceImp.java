package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImp implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    /**
     * 【调用接口】 /get/tasksets
     * 【作用】 获取任务集合
     */
    @Override
    public Result GetTaskSets(String userid,Integer is_mainline,Integer is_now){
        return Result.success(taskMapper.select_tasksets(userid,is_mainline,is_now));
    }
    /**
     * 【调用接口】 /get/tasks
     * 【作用】 获取任务集合中的具体任务
     */
    @Override
    public Result GetTasks(String setid,Integer is_now){
        return Result.success(taskMapper.select_task_by_set_id(setid,is_now));
    }
    /**
     * 【调用接口】 /get/task
     * 【作用】 获取具体任务的详细信息
     */
    @Override
    public Result GetTask(String taskid){
        return Result.success(taskMapper.select_task_by_id(taskid));
    }
    /**
     * 【调用接口】 /get/task-all
     * 【作用】 获取所有任务
     */
    @Override
    public Result GetTaskAll(String userid){
        return Result.success(taskMapper.select_task_all(userid));
    }
    /**
     * 【调用接口】 /task/confirm
     * 【作用】 公告任务确认
     */
    @Override
    public Result FinishConfirmTask(TaskConfirm taskConfirm){
        taskMapper.insert_task_fulfillment(taskConfirm);
        return Result.success(null);
    }
}
