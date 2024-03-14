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
    public Result PostForTask(PostForTaskParams params){
        if(params.getTitle()!=""&&params.getContain()!=""){
            postMapper.insert_post_task(params);
            return Result.success("发帖成功！");
        }else{
            return Result.fail(400,"未输入标题或内容",null);
        }
    }
    /**
     * 【调用接口】 /post/normail
     * 【作用】 发布常规贴
     */
    @Override
    public Result PostNormal(PostNormalParams params){
        if(params.getTitle()!=""&&params.getContain()!=""){
            postMapper.insert_post_normal(params);
            return Result.success("发帖成功！");
        }else{
            return Result.fail(400,"未输入标题或内容",null);
        }
    }
    /**
     * 【调用接口】 /post/detail
     * 【作用】 展示选中文章详情，更新点击数
     */
    @Override
    public Result PostDetail(String postid){
        if(postMapper.select_post_by_post_id(postid)!=null){
            postMapper.update_post_clicktnum_by_id(postid);
            return Result.success(postMapper.select_post_by_post_id(postid));
        }else {
            return Result.success("您想看的帖子消失了");
        }
    }
    /**
     * 【调用接口】 /post/delete/for-user
     * 【作用】 删除选中文章
     */
    @Override
    public Result PostDelete(PostDeleteParams params){
        if(postMapper.delete_post_by_id(params.getPostid())>0){
            return Result.success("删除成功");
        }else{
            return Result.success("想删除的帖子不存在");
        }
    }
    /**
     * 【调用接口】 /post/list/for-user
     * 【作用】 用户查询自己发布的文章
     */
    @Override
    public Result GetPostByUserId(String userid){
        if(postMapper.select_post_by_user_id(userid).isEmpty()){
            return Result.success("您还没有发布过文章");
        }else{
            return Result.success(postMapper.select_post_by_user_id(userid));
        }
    }
    /**
     * 【调用接口】 /post/list/for-keyword
     * 【作用】 用户根据关键词搜索文章
     */
    @Override
    public Result GetPostByKeyWord(String keyword){
        if(postMapper.select_post_by_key_word(keyword).isEmpty()){
            return Result.success("没有相关内容");
        }else{
            return Result.success(postMapper.select_post_by_key_word(keyword));
        }
    }
    /**
     * 【调用接口】 /post/list/for-task
     * 【作用】 用户获取通关攻略文章
     */
    @Override
    public Result GetPostByTaskId(){
        if(postMapper.select_post_by_task_id().isEmpty()){
            return Result.success("还没有相关内容");
        }else{
            return Result.success(postMapper.select_post_by_task_id());
        }
    }
    /**
     * 【调用接口】 /post/list/for-usercollect
     * 【作用】 用户获取收藏文章
     */
    @Override
    public Result GetPostCollectById(String userid){
        if(postMapper.select_post_collect_by_userid(userid).isEmpty()){
            return Result.success("你还没有收藏，心动不如行动哦");
        }else{
            return Result.success(postMapper.select_post_collect_by_userid(userid));
        }
    }
    @Override
    public void UpdatePostById(PostDeleteParams params){
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
