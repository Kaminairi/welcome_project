package com.laughbro.welcome.vo.params.post_params;

import lombok.Data;

import java.util.Date;

@Data
public class PostNormalParams {
    private String creator;
    private String title;
    private String contain;
    private Date ctime;
}
