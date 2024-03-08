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
     * 【作用】 获取主线任务set
     */
    @GetMapping ("/quest/main")
    public Result GetMainTask(@RequestBody TaskParams taskParams){return taskService.GetMainTask(taskParams);}
    /**
     * 【作用】 获取支线任务set
     */
    @GetMapping("/quest/nmain")
    public Result GetNmainTask(@RequestBody TaskParams taskParams){return taskService.GetNmainTask(taskParams);}
    /**
     * 【作用】 通过id获取已完成任务set
     */
    @GetMapping("/quest/finish")
    public Result GetFinishTask(@RequestBody TaskParams taskParams){return taskService.GetFinishTask(taskParams);}

    /**
     * 【作用】 通过setid获得任务列表
     */

}
