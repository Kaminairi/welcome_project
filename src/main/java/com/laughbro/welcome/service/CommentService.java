package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.comment_params.CommentAddParams;
import com.laughbro.welcome.vo.params.comment_params.CommentComParams;
import com.laughbro.welcome.vo.params.comment_params.CommentListParams;
import com.laughbro.welcome.vo.params.comment_params.CommentUserParams;

public interface CommentService {
    Result comment_list(CommentListParams commentListParams);


    Result comment_add(CommentAddParams commentAddParams);

    Result comment_like(CommentComParams commentComParams);

    Result comment_list_reply(CommentUserParams commentUserParams);

    Result comment_list_user(CommentUserParams commentUserParams);

    Result comment_update_unread(CommentUserParams commentUserParams);

    Result comment_delete(CommentComParams commentComParams);
}
