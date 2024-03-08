package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskParams;
import com.laughbro.welcome.vo.params.task_params.TasksParams;
import com.laughbro.welcome.vo.params.task_params.TaskSetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    /**
     * 【作用】 获取主线任务集合
     */
    @GetMapping ("/gettasksets/main/unfinish")
    public Result GetTaskSetMainUnfinish(@RequestBody TaskSetParams taskSetParams){return taskService.GetTaskSetMainUnfinish(taskSetParams);}
    /**
     * 【作用】 获取支线任务集合
     */
    @GetMapping("/gettasksets/nmain/unfinish")
    public Result GetTaskSetNmainUnfinish(@RequestBody TaskSetParams taskSetParams){return taskService.GetTaskSetNmainUnfinish(taskSetParams);}
    /**
     * 【作用】 通过任务组id获取未完成的任务组任务
     */
    @GetMapping("/get/tasks")
    public Result GetTasks(@RequestBody TasksParams tasksParams){return taskService.GetTasks(tasksParams);}
    /**
     * 【作用】 通过id获取任务详细信息
     */
    @GetMapping("/get/task")
    public Result GetTask(@RequestBody TaskParams params){
        return taskService.GetTask(params);
    }
}
