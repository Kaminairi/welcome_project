package com.laughbro.welcome.vo.params.post_params;

import lombok.Data;

import java.util.Date;

@Data
public class PostForTaskParams {
    private String creator;
    private String title;
    private String contain;
    private Date ctime;
    private Integer likenum;
    private Integer clicktnum;
    private Integer commentnum;
    private String TaskId;
}
