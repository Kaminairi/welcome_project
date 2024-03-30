package com.laughbro.welcome.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.dao.pojo.Post;
import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.vo.PageResult;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostMapper postMapper;
    /**
     * 【调用接口】 /post
     * 【作用】 展示所有帖子
     */
    @Override
    public Result GetPost(int page,int pagesize,int order){
        PageHelper.startPage(page, pagesize);
        Page<Post> p;
        if(order==0) {
            p = (Page<Post>) postMapper.select_post_all("desc");
        }else{
            p = (Page<Post>) postMapper.select_post_all("asc");
        }
        return p.isEmpty()?PageResult.success("暂时没有内容",p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1):PageResult.success(p.getResult(),p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1);
    }
    /**
     * 【调用接口】 /post/for-task
     * 【作用】 发布任务贴
     */
    @Override
    public Result PostForTask(PostForTaskParams params){
        if(params.getTitle().replaceAll(" ","")!=""&&params.getContain().replaceAll(" ","")!=""){
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
        if(params.getTitle().replaceAll(" ","")!=""&&params.getContain().replaceAll(" ","")!=""){
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
        Post p=postMapper.select_post_by_post_id(postid);
        if(p!=null){
            postMapper.update_post_clicktnum_by_id(postid);
            return Result.success(p);
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
            postMapper.delete_post_collect_by_postid(params.getPostid());
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
        List<Post> list=postMapper.select_post_by_user_id(userid);
        if(list.isEmpty()){
            return Result.success("您还没有发布过文章");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【调用接口】 /post/list/for-keyword
     * 【作用】 用户根据关键词搜索文章
     */
    @Override
    public Result GetPostByKeyWord(String keyword){
        List<Post> list=postMapper.select_post_by_key_word(keyword);
        if(list.isEmpty()){
            return Result.success("还没有相关内容");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【调用接口】 /post/list/for-task
     * 【作用】 用户获取通关攻略文章
     */
    @Override
    public Result GetPostByTaskId(){
        List<Post> list=postMapper.select_post_by_task_id();
        if(list.isEmpty()){
            return Result.success("还没有相关内容");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【调用接口】 /post/list/for-usercollect
     * 【作用】 用户获取收藏文章
     */
    @Override
    public Result GetPostCollectById(String userid){
        List<Post> list=postMapper.select_post_collect_by_userid(userid);
        if(list.isEmpty()){
            return Result.success("你还没有收藏，心动不如行动哦");
        }else{
            return Result.success(list);
        }
    }
    /**
     * 【调用接口】 /post/collect
     * 【作用】 用户收藏文章
     */
    @Override
    public Result PostCollect(PostCollectParams params){
        if(postMapper.select_post_by_post_id(params.getPostid())!=null){
            postMapper.insert_post_collect(params.getUserid(),params.getPostid());
            return Result.success("收藏成功");
        }else{
            return Result.success("您想收藏的文章消失了");
        }
    }
    /**
     * 【调用接口】 /post/collect/delete
     * 【作用】 用户取消收藏文章
     */
    @Override
    public Result PostCollectDelete(PostCollectParams params){
        if(postMapper.select_post_collect_by_userid(params.getUserid()).contains(params.getPostid())){
            postMapper.delete_post_collect_by_id(params);
            return Result.success("取消收藏成功");
        }else{
            return Result.success("您没有收藏该文章");
        }
    }
    /**
     * 【调用接口】 /post/edit
     * 【作用】 用户编辑文章
     */
    @Override
    public Result PostEdit(PostEditParams params){
        if(params.getTitle().replaceAll(" ","")!=""&&params.getContain().replaceAll(" ","")!="") {
            if (postMapper.update_post_by_id(params) > 0) {
                return Result.success("修改成功");
            } else {
                return Result.success("修改失败");
            }
        }else{
            return Result.success("没有输入标题或内容");
        }
    }
    /**
     * 【调用接口】 /post/like
     * 【作用】 用户点赞文章
     */
    @Override
    public Result PostLike(String postid){
        if(postMapper.select_post_by_post_id(postid)!=null){
            postMapper.update_post_likenum_by_id(postid);
            return Result.success(postMapper.select_post_by_post_id(postid));
        }else{
            return Result.success("要点赞的帖子消失了");
        }
    }
    /**
     * 【调用接口】 /admin/post
     * 【作用】 管理员论坛首页
     */
    @Override
    public PageResult AdGetPost(int page, int pagesize, int order){
        PageHelper.startPage(page, pagesize);
        Page<Post> p;
        if(order==0) {
            p = (Page<Post>) postMapper.select_post_all("desc");
        }else{
            p = (Page<Post>) postMapper.select_post_all("asc");
        }
        return p.isEmpty()?PageResult.success("暂时没有内容",p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1):PageResult.success(p.getResult(),p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1);
    }

}
