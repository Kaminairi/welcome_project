package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.PostDetailParams;
import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import com.laughbro.welcome.vo.params.post_params.PostListForUserParams;
import com.laughbro.welcome.vo.params.post_params.PostNormalParams;
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
}
