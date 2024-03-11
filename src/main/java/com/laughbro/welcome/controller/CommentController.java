package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.CommentService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.comment_params.CommentAddParams;
import com.laughbro.welcome.vo.params.comment_params.CommentComParams;
import com.laughbro.welcome.vo.params.comment_params.CommentListParams;
import com.laughbro.welcome.vo.params.comment_params.CommentUserParams;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 【作用】 获得某个文章的所有评论列表
     */
    @GetMapping("/comment/list/for-post")
    public Result comment_list(BigInteger postid,int time_order,int like_order){
        return commentService.comment_list(postid,time_order,like_order);
    }
    /**
     * 【作用】 发表评论
     */
    @PostMapping("/comment")
    public Result comment_add(@RequestBody CommentAddParams commentAddParams){
        return commentService.comment_add(commentAddParams);
    }
    /**
     * 【作用】 为某个评论点赞
     */
    @PatchMapping("/comment/like")
    public Result comment_like(@RequestBody CommentComParams commentComParams){
        return commentService.comment_like(commentComParams);
    }
    /**
     * 【作用】 获得某个用户发表的评论
     */
    @GetMapping ("/comment/list/for-user")
    public Result comment_list_user(String id){
        return commentService.comment_list_user(id);
    }

    /**
     * 【作用】 获得某个用户作为被回复者的评论
     */
    @GetMapping("/comment/list/for-reply")
    public Result comment_list_reply(String id){
        return commentService.comment_list_reply(id);
    }

    /**
     * 【作用】 删除某个评论
     */
    @DeleteMapping("/comment/delete")
    public Result comment_delete(@RequestBody CommentComParams commentComParams){
        return commentService.comment_delete(commentComParams);
    }
    /**
     * 【作用】 将某个用户所有的未读评论设为已读
     */
    @PatchMapping("/comment/update-unread")
    public Result comment_update_unread(@RequestBody CommentUserParams commentUserParams){
        return commentService.comment_update_unread(commentUserParams);
    }
    /**
     * 【作用】 将某个用户的未读评论设为已读，用于跳转
     */
    @PatchMapping("/comment/update-unread-one")
    public Result comment_update_unread_one(@RequestBody CommentComParams commentComParams){
        return commentService.comment_update_unread_one(commentComParams);
    }

}
