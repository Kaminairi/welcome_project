package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 【作用】进入论坛主页显示全部文章（未排序）
     */
    @GetMapping("/post")
    public Result GetPost(){
        return postService.GetPost();
    }

    /**
     *【作用】根据任务发表攻略文章
     */
    @PostMapping("/post/for-task")
    public Result PostForTask(@RequestBody PostForTaskParams params){
        return postService.PostForTask(params);
    }

    /**
     * 【作用】发表普通文章
     */
    @PostMapping("/post/normal")
    public Result PostNormal(@RequestBody PostNormalParams params){
        return postService.PostNormal(params);
    }

    /**
     * 【作用】点击文章显示文章详情
     */
    @GetMapping("/post/detail")
    public Result PostDetail(String postid){
        return postService.PostDetail(postid);
    }
    /**
     * 【作用】用户删除帖子
     */
    @DeleteMapping("/post/delete/for-user")
    public Result PostDelet(@RequestBody PostDeleteParams params){
        return postService.PostDelete(params);
    }
    /**
     * 【作用】用户查询自己发布的文章
     */
    @GetMapping("/post/list/for-user")
    public Result PostListForUser(String userid){
        return postService.GetPostByUserId(userid);
    }
    /**
     * 【作用】搜索文章
     */
    @GetMapping("/post/list/for-keyword")
    public Result PostSearch(String keyword){
        return postService.GetPostByKeyWord(keyword);
    }
    /**
     * 【作用】通关攻略文章
     */
    @GetMapping("/post/list/for-task")
    public Result PostListForTask(){
        return postService.GetPostByTaskId();
    }
    /**
     * 【作用】显示用户收藏文章
     */
    @GetMapping("/post/list/for-usercollect")
    public Result PostUserCollect(String userid){
        return postService.GetPostCollectById(userid);
    }
    /**
     * 【作用】点赞
     */
    @PutMapping("/post/like")
    public Result PostLike(@RequestBody PostDeleteParams params){
        postService.UpdatePostById(params);
        return postService.GetPostById(params.getPostid());
    }
    /**
     * 【作用】收藏文章
     */
    @PostMapping("/post/collect")
    public Result PostCollect(@RequestBody PostCollectParams params){
        postService.PostCollect(params);
        return Result.success(null);
    }
    /**
     * 【作用】删除收藏
     */
    @DeleteMapping("/post/collect/delete")
    public Result PostCollectDelete(@RequestBody PostCollectParams params){
        postService.PostCollectDelete(params);
        return Result.success(null);
    }
    /**
     * 【作用】编辑文章
     */
    @PatchMapping ("/post/edit")
    public Result PostEdit(@RequestBody PostEditParams params){
        postService.PostEdit(params);
        return Result.success(null);
    }
}
