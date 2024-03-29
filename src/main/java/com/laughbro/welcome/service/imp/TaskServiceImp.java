package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.TaskFulfillment;
import com.laughbro.welcome.dao.pojo.TaskPic;
import com.laughbro.welcome.dao.pojo.TaskSet;
import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.*;
import com.laughbro.welcome.vo.params.taskpic_params.TaskPicParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        return Result.success("任务完成!");
    }
    /**
     * 【作用】 管理员发布任务集合
     */
    @Override
    public Result AdPostTaskSet(TaskSetPostParams params){
        try {
            if(taskMapper.insert_taskset(params)==1){
                return Result.success("发布成功");
            }else{
                return Result.fail(100,"fail","发布失败");
            }
        }catch (Exception e){
            return Result.fail(100,"fail","发布失败");
        }

    }
    /**
     * 【作用】 管理员获取任务集合
     */
    @Override
    public Result AdGetTaskSets(){
        List<TaskSet> list=taskMapper.select_tasksets_all();
        if(list.isEmpty()){
            return Result.success("暂时还没有任务集合");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【作用】 管理员发布任务
     */
    @Override
    public Result AdPostTask(TaskPostParams params){
        try{
            if(taskMapper.insert_task(params)==1){
                return Result.success("发布成功");
            }else{
                return Result.fail(100,"fail","发布失败");
            }
        }catch (Exception e){
            return Result.fail(100,"fail","发布失败");
        }

    }
    /**
     * 【作用】 管理员删除任务
     */
    @Override
    public Result AdDeleteTask(TaskParams params){
        if(taskMapper.delete_task_by_id(params.getId())==1){
            taskMapper.delete_task_fulfillment_by_taskid(params.getId());
            return Result.success("删除成功");
        }else{
            return Result.fail(100,"fail","删除失败");
        }
    }
    /**
     * 【作用】 管理员编辑任务
     */
    @Override
    public Result AdEditTask(TaskEditParams params){
        try {
            if(taskMapper.update_task_by_id(params)==1){
                return Result.success("修改成功");
            }else{
                return Result.fail(100,"fail","修改失败");
            }
        }catch (Exception e){
            return Result.fail(100,"fail","修改失败");
        }

    }
    /**
     * 【作用】 管理员获取集合中的任务
     */
    @Override
    public Result AdGetTaskBySetId(String setid){
        List<Task> list=taskMapper.select_task_by_set_id_ad(setid);
        if(list.isEmpty()){
            return Result.success("暂时还没有任务");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【作用】 管理员搜索任务
     */
    @Override
    public Result AdGetTaskByKeyword(String keyword){
        List<Task> list=taskMapper.select_task_by_keyword(keyword);
        if(list.isEmpty()){
            return Result.success("没有相关任务");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【作用】 管理员查看提交
     */
    @Override
    public Result GetTaskPic(){
        List<TaskPic> list=taskMapper.select_taskpic_all();
        if(list.isEmpty()){
            return Result.success("还没有提交记录");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【作用】 管理员审核通过任务提交
     */
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
        return Result.success("审核通过");
    }
    /**
     * 【作用】 管理员获取完成记录
     */
    @Override
    public Result GetTaskFulfillment(){
        List<TaskFulfillment> list=taskMapper.select_task_fulfillment_all();
        if(list.isEmpty()){
            return Result.success("没有完成记录");
        }else{
            return Result.success(list);
        }
    }
}
