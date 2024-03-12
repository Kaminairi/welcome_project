package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.BagService;
import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

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

    @GetMapping("/get/task-all")
    public Result GetTaskAll(String userid){
        return taskService.GetTaskAll(userid);
    }





}
