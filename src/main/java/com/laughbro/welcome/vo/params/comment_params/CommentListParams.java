package com.laughbro.welcome.vo.params.comment_params;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CommentListParams {
    private BigInteger postid;

    private int time_order;

    private int like_order;
}
