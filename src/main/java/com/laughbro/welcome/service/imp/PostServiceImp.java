package com.laughbro.welcome.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.laughbro.welcome.dao.mapper.AdExprosureRecordMapper;
import com.laughbro.welcome.dao.mapper.AdvertMapper;
import com.laughbro.welcome.dao.mapper.PostMapper;
import com.laughbro.welcome.dao.pojo.Advert;
import com.laughbro.welcome.dao.pojo.Post;
import com.laughbro.welcome.service.AdvertService;
import com.laughbro.welcome.service.PostService;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.vo.PageResult;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.post_params.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private AdvertMapper advertMapper;
    @Autowired
    private TimeUtils timeUtils;
    @Autowired
    private AdExprosureRecordMapper adExprosureRecordMapper;
    /**
     * 【调用接口】 /post
     * 【作用】 展示所有帖子
     */
    //如果遇到冲突处理，请保留我的代码，这里急需改动
    //!!!!!!!!!
    //!!!!!!!!!
    @Override
    public Result GetPost(String userid,int page,int pagesize,int order){
        //分页
        //广告帖子的数量
        int adnum=2;
        PageHelper.startPage(page, pagesize-2);
        Page<Post> p;
        if(order==0) {
            p = (Page<Post>) postMapper.select_post_all("desc");
        }else{
            p = (Page<Post>) postMapper.select_post_all("asc");
        }
        if(p.isEmpty())
            return Result.fail(201,"没有结果了",null);
        List<Post> add_ad_postlist=p.getResult();
        //加载广告
        List<Advert> ads=advertMapper.select_ad_limit2_rand();
        //分装到post里
        String extime=timeUtils.timeGetNow();
        Advert ad1=ads.get(0);
        Advert ad2=ads.get(1);
        Post post1=new Post();
        post1.setName(ad1.getName()+"【广告】");
        post1.setAdid(ad1.getId());
        post1.setContain(ad1.getContent());
        post1.setImg(ad1.getImg());
        post1.setIs_adpost(1);
        post1.setUrl(ad1.getUrl());
        post1.setTitle(ad1.getName()+"【广告】");
        post1.setExtime(extime);
        Post post2=new Post();
        post2.setName(ad2.getName()+"【广告】");
        post2.setAdid(ad2.getId());
        post2.setContain(ad2.getContent());
        post2.setImg(ad2.getImg());
        post2.setIs_adpost(1);
        post2.setUrl(ad2.getUrl());
        post2.setTitle(ad2.getName()+"【广告】");
        post2.setExtime(extime);
        //塞入列表
        Random random = new Random();
        int randomNumber1 = random.nextInt(pagesize-1);
        int randomNumber2 = random.nextInt(pagesize-1);
        add_ad_postlist.add(randomNumber1,post1);
        add_ad_postlist.add(randomNumber2,post2);
        //曝光
        adExprosureRecordMapper.insert_adex(userid,ad1.getId(),extime);
        adExprosureRecordMapper.insert_adex(userid,ad2.getId(),extime);


        //return p.isEmpty()?PageResult.success("暂时没有内容",p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1):PageResult.success(p.getResult(),p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1);
        return  Result.success(add_ad_postlist);
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
    public Result PostDetail(String userid,String postid){
        Post p=postMapper.select_post_by_post_id(postid);
        if(p!=null){
            if (postMapper.select_post_collect_by_userid_postid(userid, postid)!=null) {
                p.setIscollect("1");
            } else {
                p.setIscollect("0");
            }
            postMapper.update_post_clicktnum_by_id(postid);
            return Result.success(p);
        }else {
            return Result.success("您想看的帖子消失了");
        }
    }

    @Override
    public Result PostadDetail(String userid, String extime, String ad_id) {
        //创建这个虚假的post
        //查询广告
        Advert ad2=advertMapper.select_advert_by_id(ad_id);
        Post post2=new Post();
        post2.setName(ad2.getName()+"【广告】");
        post2.setAdid(ad2.getId());
        post2.setContain(ad2.getContent());
        post2.setImg(ad2.getImg());
        post2.setIs_adpost(1);
        post2.setUrl(ad2.getUrl());
        post2.setTitle(ad2.getName()+"【广告】");
        post2.setExtime(extime);
        //修改点击记录
        adExprosureRecordMapper.update_adex_cl(userid,timeUtils.timeGetNow(),extime,ad_id);

        return Result.success(post2);
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
