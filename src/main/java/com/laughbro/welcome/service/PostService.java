package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.PageResult;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;

public interface PostService {
    Result GetPost(String userid,int page,int pagesize,int order);
    Result PostForTask(PostForTaskParams params);
    Result PostNormal(PostNormalParams params);

    Result PostDetail(String userid,String postid);

    Result PostadDetail(String userid,String extime,String ad_id);

    Result PostDelete(PostDeleteParams params);

    Result GetPostByUserId(String userid);

    Result GetPostByKeyWord(String keyword);

    Result GetPostByTaskId();

    Result GetPostCollectById(String userid);

    Result PostCollect(PostCollectParams params);

    Result PostCollectDelete(PostCollectParams params);

    Result PostEdit(PostEditParams params);

    Result PostLike(String postid);

    PageResult AdGetPost(int page, int pagesize, int order);
}
