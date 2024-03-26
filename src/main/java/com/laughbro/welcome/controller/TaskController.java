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
    public Result GetTasks(String setid,Integer is_now){return taskService.GetTasks(setid,is_now);}
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
    @PostMapping("/task/confirm")
    public Result TaskConfirm(@RequestBody TaskConfirm taskConfirm){
        return taskService.FinishConfirmTask(taskConfirm);
    }

    @RequestMapping("/admin/post/taskset")
    public Result AdPostTaskSet(@RequestBody TaskSetPostParams params){
        return taskService.AdPostTaskSet(params);
    }

    @RequestMapping("/admin/get/taskset")
    public Result AdPostTaskSet(){
        return taskService.AdGetTaskSets();
    }

    @RequestMapping("/admin/post/task")
    public Result AdPosttask(@RequestBody TaskPostParams params){
        return taskService.AdPostTask(params);
    }

    @RequestMapping("/admin/delete/task")
    public Result AdDeleteTask(@RequestBody TaskParams params){
        return taskService.AdDeleteTask(params);
    }

    @RequestMapping("/admin/edit/task")
    public Result AdEditTask(@RequestBody TaskEditParams params){
        return taskService.AdEditTask(params);
    }

    @RequestMapping("/admin/gettask/bysetid")
    public Result AdGetTaskBySetId(String setid){
        return taskService.AdGetTaskBySetId(setid);
    }

    @RequestMapping("/admin/gettask/bykeyword")
    public Result AdGetTaskByKeyword(String keyword){
        return taskService.AdGetTaskByKeyword(keyword);
    }

    @RequestMapping("/admin/check/tasksubmit")
    public Result AdGetTaskPic(){
        return taskService.GetTaskPic();
    }

    @RequestMapping("/admin/pass/tasksubmit")
    public Result AdPassTaskPic(@RequestBody TaskPicParams params){
        return taskService.PassTaskPic(params);
    }

    @RequestMapping("/admin/get/taskfulfillment")
    public Result AdGetTaskFulfillment(){
        return taskService.GetTaskFulfillment();
    }
}
