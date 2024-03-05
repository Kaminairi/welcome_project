package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;

public interface PostService {
    Result GetPost();
    void PostForTask(PostForTaskParams params);
    void PostNormal(PostNormalParams params);

    Result PostDetail(PostDetailParams params);

    void PostDelete(PostDetailParams params);

    Result GetPostById(PostListForUserParams params);

    Result GetPostByKeyWord(PostSearchParams params);

    Result GetPostByTaskId();

    Result GetPostCollectById(PostListForUserParams params);
}
