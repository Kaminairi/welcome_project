package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.SortService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SortController {
    @Autowired
    private SortService sortService;

    @GetMapping("/sort/finish-task")
    public Result GetTaskFinish(){
        return sortService.GetTaskFInish();
    }
    @GetMapping("/admin/sort/finish-task")
    public  Result AdGetTaskFinish(){
        return sortService.GetTaskFInish();
    }
}
