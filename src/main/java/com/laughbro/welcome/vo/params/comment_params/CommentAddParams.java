package com.laughbro.welcome.vo.params.comment_params;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CommentAddParams {

    private  BigInteger fa_post;
    private BigInteger fa_comment;
    private String creator;
    private int is_facomment;
    private String reply;
    private String contain;
}
