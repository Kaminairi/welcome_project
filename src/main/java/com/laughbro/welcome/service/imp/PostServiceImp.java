package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostMapper postMapper;
    /**
     * 【调用接口】 /post
     * 【作用】 展示所有帖子
     */
    @Override
    public Result GetPost(){
        return Result.success(postMapper.select_post_all());
    }
    /**
     * 【调用接口】 /post/for-task
     * 【作用】 发布任务贴
     */
    @Override
    public void PostForTask(PostForTaskParams params){
        postMapper.insert_post_task(params);
    }
    /**
     * 【调用接口】 /post/normail
     * 【作用】 发布常规贴
     */
    @Override
    public void PostNormal(PostNormalParams params){
        postMapper.insert_post_normal(params);
    }
    /**
     * 【调用接口】 /post/detail
     * 【作用】 展示选中文章详情，更新点击数
     */
    @Override
    public Result PostDetail(PostDetailParams params){
        postMapper.update_post_clicktnum_by_id(params.getId());
        return Result.success(postMapper.select_post_by_post_id(params.getId()));
    }
    @Override
    public void PostDelete(PostDetailParams params){
        postMapper.delete_post_by_id(params.getId());
    }
    @Override
    public Result GetPostById(PostListForUserParams params){
        return Result.success(postMapper.select_post_by_user_id(params.getId()));
    }
    @Override
    public Result GetPostByKeyWord(PostSearchParams params){
        return Result.success(postMapper.select_post_by_key_word(params.getKeyword()));
    }
    @Override
    public Result GetPostByTaskId(){
        return Result.success(postMapper.select_post_by_task_id());
    }
    @Override
    public Result GetPostCollectById(PostListForUserParams params){
        return Result.success(postMapper.select_post_collect_by_id(params.getId()));
    }
    @Override
    public void UpdatePostById(PostDetailParams params){
        postMapper.update_post_likenum_by_id(params.getId());
    }
    @Override
    public void PostCollect(PostCollectParams params){
        System.out.println(params.toString());
        postMapper.insert_post_collect(params.getUserid(),params.getPostid());
    }
}
