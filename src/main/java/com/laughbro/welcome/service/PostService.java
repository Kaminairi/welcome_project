package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;

public interface PostService {
    Result GetPost();
    void PostForTask(PostForTaskParams params);
    void PostNormal(PostNormalParams params);

    Result PostDetail(String postid);

    void PostDelete(PostDetailParams params);

    Result GetPostByUserId(String userid);

    Result GetPostByKeyWord(String keyword);

    Result GetPostByTaskId();

    Result GetPostCollectById(String userid);

    void UpdatePostById(PostDetailParams params);

    void PostCollect(PostCollectParams params);

    void PostCollectDelete(PostCollectParams params);

    void PostEdit(PostEditParams params);

    Result GetPostById(String postid);
}
