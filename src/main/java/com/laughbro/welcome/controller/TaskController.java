package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.TaskService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping("/quest")
    public Result GetTask(){
        return Result.success(taskService.GetTask());
    }

}
