package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Comment
{
    private BigInteger id;

    private BigInteger faPost;

    private BigInteger faComment;

    private String creator;

    private String creatorname;

    private String creatorimg;

    private  String ctime;

    private int likenum;

    private int  isFacomment;

    private String reply;

    private String replyname;

    private String replyimg;

    private String contain;

    private int replyread;


}
