package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.CommentService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.comment_params.CommentAddParams;
import com.laughbro.welcome.vo.params.comment_params.CommentComParams;
import com.laughbro.welcome.vo.params.comment_params.CommentListParams;
import com.laughbro.welcome.vo.params.comment_params.CommentUserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;



    @PostMapping("/comment/list/for-post")
    public Result comment_list(@RequestBody CommentListParams commentListParams){
        return commentService.comment_list(commentListParams);
    }

    @PostMapping("/comment")
    public Result comment_add(@RequestBody CommentAddParams commentAddParams){
        return commentService.comment_add(commentAddParams);
    }

    @PostMapping("/comment/like")
    public Result comment_like(@RequestBody CommentComParams commentComParams){
        return commentService.comment_like(commentComParams);
    }

    @PostMapping("/comment/list/for-user")
    public Result comment_list_user(@RequestBody CommentUserParams commentUserParams){
        return commentService.comment_list_user(commentUserParams);
    }


    @PostMapping("/comment/list/for-reply")
    public Result comment_list_reply(@RequestBody CommentUserParams commentUserParams){
        return commentService.comment_list_reply(commentUserParams);
    }


    @PostMapping("/comment/delete")
    public Result comment_delete(@RequestBody CommentComParams commentComParams){
        return commentService.comment_delete(commentComParams);
    }

    @PostMapping("/comment/update-unread")
    public Result comment_update_unread(@RequestBody CommentUserParams commentUserParams){
        return commentService.comment_update_unread(commentUserParams);
    }


}
