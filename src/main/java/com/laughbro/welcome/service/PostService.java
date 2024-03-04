package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.PostDetailParams;
import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import com.laughbro.welcome.vo.params.post_params.PostNormalParams;

public interface PostService {
    Result GetPost();
    void PostForTask(PostForTaskParams params);
    void PostNormal(PostNormalParams params);

    Result PostDetail(PostDetailParams params);
}
