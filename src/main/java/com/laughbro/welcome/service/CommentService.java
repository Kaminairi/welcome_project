package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.comment_params.CommentAddParams;
import com.laughbro.welcome.vo.params.comment_params.CommentComParams;
import com.laughbro.welcome.vo.params.comment_params.CommentListParams;
import com.laughbro.welcome.vo.params.comment_params.CommentUserParams;

import java.math.BigInteger;

public interface CommentService {
    Result comment_list(BigInteger postid, int time_order, int like_order);


    Result comment_add(CommentAddParams commentAddParams);

    Result comment_like(CommentComParams commentComParams);

    Result comment_list_reply(String id);

    Result comment_list_user(String id);

    Result comment_update_unread(CommentUserParams commentUserParams);

    Result comment_delete(CommentComParams commentComParams);

    Result comment_update_unread_one(CommentComParams commentComParams);

    Result comment_list_keyword(String keyword);

    Result comment_all(int page, int pagesize);
}
