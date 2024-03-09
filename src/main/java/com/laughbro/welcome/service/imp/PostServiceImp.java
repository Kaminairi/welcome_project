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
    public Result PostDetail(String postid){
        postMapper.update_post_clicktnum_by_id(postid);
        return Result.success(postMapper.select_post_by_post_id(postid));
    }
    @Override
    public void PostDelete(PostDetailParams params){
        postMapper.delete_post_by_id(params.getPostid());
    }
    @Override
    public Result GetPostByUserId(String userid){
        return Result.success(postMapper.select_post_by_user_id(userid));
    }
    @Override
    public Result GetPostByKeyWord(String keyword){
        return Result.success(postMapper.select_post_by_key_word(keyword));
    }
    @Override
    public Result GetPostByTaskId(){
        return Result.success(postMapper.select_post_by_task_id());
    }
    @Override
    public Result GetPostCollectById(String userid){
        return Result.success(postMapper.select_post_collect_by_id(userid));
    }
    @Override
    public void UpdatePostById(PostDetailParams params){
        postMapper.update_post_likenum_by_id(params.getPostid());
    }
    @Override
    public void PostCollect(PostCollectParams params){
        postMapper.insert_post_collect(params.getUserid(),params.getPostid());
    }
    @Override
    public void PostCollectDelete(PostCollectParams params){
        postMapper.delete_post_collect_by_id(params);
    }

    @Override
    public void PostEdit(PostEditParams params){
        postMapper.update_post_by_id(params);
    }
    @Override
    public Result GetPostById(String postid){
        return Result.success(postMapper.select_post_by_post_id(postid));
    }
}
