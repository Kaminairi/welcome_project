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
     * //崔修改
     */
    @GetMapping("/post")
    public Result GetPost(String userid,int page,int pagesize,int order){
        return postService.GetPost(userid,page,pagesize,order);
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
     *
     */
    @GetMapping("/post/detail")
    public Result PostDetail(String userid,String postid){
        return postService.PostDetail(userid,postid);
    }

    /**
     * 【作用】点击   ad伪装的   文章显示文章详情/
     * 崔++
     */

    @GetMapping("/post/addetail")
    public Result PostadDetail(String userid,String extime,String ad_id){
        return postService.PostadDetail(userid,extime,ad_id);
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
        return postService.PostLike(params.getPostid());
    }
    /**
     * 【作用】收藏文章
     */
    @PostMapping("/post/collect")
    public Result PostCollect(@RequestBody PostCollectParams params){
        return postService.PostCollect(params);
    }
    /**
     * 【作用】删除收藏
     */
    @DeleteMapping("/post/collect/delete")
    public Result PostCollectDelete(@RequestBody PostCollectParams params){
        return postService.PostCollectDelete(params);
    }
    /**
     * 【作用】编辑文章
     */
    @PatchMapping ("/post/edit")
    public Result PostEdit(@RequestBody PostEditParams params){
        return postService.PostEdit(params);
    }

    /**
     * 【作用】管理员论坛首页
     */
    @GetMapping("/admin/post")
    public Result AdGetPost(int page,int pagesize,int order){
        return postService.AdGetPost(page,pagesize,order);
    }

    /**
     * 【作用】管理员删除帖子
     */
    @DeleteMapping("/admin/post/delete")
    public Result AdPostDelete(@RequestBody PostDeleteParams params){
        return postService.PostDelete(params);
    }
    /**
     * 【作用】管理员发任务贴
     */
    @PostMapping("/admin/post/for-task")
    public Result AdPostForTask(@RequestBody PostForTaskParams params){
        return postService.PostForTask(params);
    }

    /**
     * 【作用】管理员发常规贴
     */
    @PostMapping("/admin/post/normal")
    public Result AdPostNormal(@RequestBody PostNormalParams params){
        return postService.PostNormal(params);
    }
    /**
     * 【作用】管理员搜索帖子
     */
    @GetMapping("/admin/post/for-keyword")
    public Result AdPostSearch(String keyword){
        return postService.GetPostByKeyWord(keyword);
    }
}
