package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import com.laughbro.welcome.vo.params.post_params.PostNormalParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public Result GetPost(){
        return postService.GetPost();
    }

    @PostMapping("/post/for-task")
    public Result PostForTask(@RequestBody PostForTaskParams params){
        postService.PostForTask(params);
        return Result.success(null);
    }

    @PostMapping("/post/normal")
    public Result PostNormal(@RequestBody PostNormalParams params){
        postService.PostNormal(params);
        return Result.success(null);
    }

}
