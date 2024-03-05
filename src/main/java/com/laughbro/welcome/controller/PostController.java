package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 【作用】进入论坛主页显示全部文章（未排序）
     */
    @PostMapping("/post")
    public Result GetPost(){
        return postService.GetPost();
    }

    /**
     *【作用】根据任务发表攻略文章
     */
    @PostMapping("/post/for-task")
    public Result PostForTask(@RequestBody PostForTaskParams params){
        postService.PostForTask(params);
        return Result.success(null);
    }

    /**
     * 【作用】发表普通文章
     */
    @PostMapping("/post/normal")
    public Result PostNormal(@RequestBody PostNormalParams params){
        postService.PostNormal(params);
        return Result.success(null);
    }

    /**
     * 【作用】点击文章显示文章详情
     */
    @PostMapping("/post/detail")
    public Result PostDetail(@RequestBody PostDetailParams params){
        return postService.PostDetail(params);
    }

    @PostMapping("/post/delete/for-user")
    public Result PostDelet(@RequestBody PostDetailParams params){
        postService.PostDelete(params);
        return Result.success("success");
    }

    @PostMapping("/post/list/for-user")
    public Result PostListForUser(@RequestBody PostListForUserParams params){
        return postService.GetPostById(params);
    }
    @PostMapping("/post/list/for-keyword")
    public Result PostSearch(@RequestBody PostSearchParams params){
        return postService.GetPostByKeyWord(params);
    }

    @PostMapping("/post/list/for-task")
    public Result PostListForTask(){
        return postService.GetPostByTaskId();
    }
    @PostMapping("/post/list/for-usercollect")
    public Result PostUserCollect(@RequestBody PostListForUserParams params){
        return postService.GetPostCollectById(params);
    }


}
