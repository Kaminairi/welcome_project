package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.TaskParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    /**
     * 【作用】 获取主线任务
     */
    @GetMapping ("/quest/main")
    public Result GetMainTask(){return taskService.GetMainTask();}
    /**
     * 【作用】 获取支线任务
     */
    @GetMapping("/quest/nmain")
    public Result GetNmainTask(){return taskService.GetNmainTask();}
    /**
     * 【作用】 通过id获取已完成任务
     */
    @PostMapping("/quest/finish")
    public Result GetFinishTask(@RequestBody TaskParams taskParams){return taskService.GetFinishTask(taskParams);}
}
