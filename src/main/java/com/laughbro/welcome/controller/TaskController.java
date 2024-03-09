package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    /**
     * 【作用】 获取主线任务集合
     */
    @GetMapping ("/gettasksets/main/unfinish")
    public Result GetTaskSetMainUnfinish(String userid){return taskService.GetTaskSetMainUnfinish(userid);}
    /**
     * 【作用】 获取支线任务集合
     */
    @GetMapping("/gettasksets/nmain/unfinish")
    public Result GetTaskSetNmainUnfinish(String userid){return taskService.GetTaskSetNmainUnfinish(userid);}
    /**
     * 【作用】 通过任务组id获取未完成的任务组任务
     */
    @GetMapping("/get/tasks")
    public Result GetTasks(String setid){return taskService.GetTasks(setid);}
    /**
     * 【作用】 通过id获取任务详细信息
     */
    @GetMapping("/get/task")
    public Result GetTask(String taskid){
        return taskService.GetTask(taskid);
    }
}
