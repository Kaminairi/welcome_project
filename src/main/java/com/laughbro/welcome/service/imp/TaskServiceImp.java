package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.*;
import com.laughbro.welcome.vo.params.taskpic_params.TaskPicParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public Result GetTasks(String setid,String userid,Integer is_now){
        return Result.success(taskMapper.select_task_by_set_id(setid,userid,is_now));
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
    @Override
    public Result AdPostTaskSet(TaskSetPostParams params){
        return Result.success(taskMapper.insert_taskset(params));
    }

    @Override
    public Result AdGetTaskSets(){
        return Result.success(taskMapper.select_tasksets_all());
    }

    @Override
    public Result AdPostTask(TaskPostParams params){
        return Result.success(taskMapper.insert_task(params));
    }

    @Override
    public Result AdDeleteTask(TaskParams params){
        return Result.success(taskMapper.delete_task_by_id(params.getId()));
    }

    @Override
    public Result AdEditTask(TaskEditParams params){
        return Result.success(taskMapper.update_task_by_id(params));
    }
    @Override
    public Result AdGetTaskBySetId(String setid){
        return Result.success(taskMapper.select_task_by_set_id_ad(setid));
    }

    @Override
    public Result AdGetTaskByKeyword(String keyword){
        return Result.success(taskMapper.select_task_by_keyword(keyword));
    }
    @Override
    public Result GetTaskPic(){
        return Result.success(taskMapper.select_taskpic_all());
    }
    @Override
    public Result PassTaskPic(TaskPicParams params){
        taskMapper.update_taskpic(params);
        TaskConfirm taskConfirm=new TaskConfirm();
        taskConfirm.setUserid(params.getUserid());
        taskConfirm.setTaskid(params.getTaskid());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        taskConfirm.setTime(formatDateTime);
        taskMapper.insert_task_fulfillment(taskConfirm);
        return Result.success(null);
    }
    @Override
    public Result GetTaskFulfillment(){
        return Result.success(taskMapper.select_task_fulfillment_all());
    }
}
