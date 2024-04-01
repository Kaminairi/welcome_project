package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.*;
import com.laughbro.welcome.vo.params.taskpic_params.TaskPicParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    /**
     * 【作用】 获取任务集合
     */
    @GetMapping ("/get/tasksets")
    public Result GetTaskSets(String userid,Integer is_mainline,Integer is_now){return taskService.GetTaskSets(userid,is_mainline,is_now);}
    /**
     * 【作用】 通过任务组id获取未完成的任务组任务
     */
    @GetMapping("/get/tasks")
    public Result GetTasks(String setid,String userid,Integer is_now){return taskService.GetTasks(setid,userid,is_now);}
    /**
     * 【作用】 通过id获取任务详细信息
     */
    @GetMapping("/get/task")
    public Result GetTask(String taskid){
        return taskService.GetTask(taskid);
    }
    /**
     * 【作用】 通过id获取所有任务
     */
    @GetMapping("/get/task-all")
    public Result GetTaskAll(String userid){
        return taskService.GetTaskAll(userid);
    }
    /**
     * 【作用】 公告任务确认键
     */
    @PostMapping("/task/confirm")
    public Result TaskConfirm(@RequestBody TaskConfirm taskConfirm){
        return taskService.FinishConfirmTask(taskConfirm);
    }
    /**
     * 【作用】 管理员发布任务集合
     */
    @RequestMapping("/admin/post/taskset")
    public Result AdPostTaskSet(@RequestBody TaskSetPostParams params){
        return taskService.AdPostTaskSet(params);
    }
    /**
     * 【作用】 管理员获取任务集合
     */
    @RequestMapping("/admin/get/taskset")
    public Result AdPostTaskSet(int page,int pagesize){
        return taskService.AdGetTaskSets(page,pagesize);
    }
    /**
     * 【作用】 管理员发布任务
     */
    @RequestMapping("/admin/post/task")
    public Result AdPosttask(@RequestBody TaskPostParams params){
        return taskService.AdPostTask(params);
    }
    /**
     * 【作用】 管理员删除任务
     */
    @RequestMapping("/admin/delete/task")
    public Result AdDeleteTask(@RequestBody TaskParams params){
        return taskService.AdDeleteTask(params);
    }
    /**
     * 【作用】 管理员编辑任务
     */
    @RequestMapping("/admin/edit/task")
    public Result AdEditTask(@RequestBody TaskEditParams params){
        return taskService.AdEditTask(params);
    }
    /**
     * 【作用】 管理员获取集合中的任务
     */
    @RequestMapping("/admin/gettask/bysetid")
    public Result AdGetTaskBySetId(int page,int pagesize,String setid){
        return taskService.AdGetTaskBySetId(page,pagesize,setid);
    }
    /**
     * 【作用】 管理员搜索任务
     */
    @RequestMapping("/admin/gettask/bykeyword")
    public Result AdGetTaskByKeyword(String keyword){
        return taskService.AdGetTaskByKeyword(keyword);
    }
    /**
     * 【作用】 管理员查看提交
     */
    @RequestMapping("/admin/check/tasksubmit")
    public Result AdGetTaskPic(){
        return taskService.GetTaskPic();
    }
    /**
     * 【作用】 管理员审核通过任务提交
     */
    @RequestMapping("/admin/pass/tasksubmit")
    public Result AdPassTaskPic(@RequestBody TaskPicParams params){
        return taskService.PassTaskPic(params);
    }
    /**
     * 【作用】 管理员获取完成记录
     */
    @RequestMapping("/admin/get/taskfulfillment")
    public Result AdGetTaskFulfillment(){
        return taskService.GetTaskFulfillment();
    }
}
