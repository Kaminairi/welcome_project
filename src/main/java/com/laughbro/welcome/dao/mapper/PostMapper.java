package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Post;
import com.laughbro.welcome.vo.params.post_params.PostCollectParams;
import com.laughbro.welcome.vo.params.post_params.PostEditParams;
import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import com.laughbro.welcome.vo.params.post_params.PostNormalParams;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    /**
     * 【调用接口】 /post
     * 【作用】 进入论坛主页查询所有文章
     * @return 存在返回所有文章信息
     *         不存在返回  null
     */
    @Select("<script>"
            + "select users.name,users.img,posts.* from users,posts "
            + "where posts.creator=users.id "
            + "order by posts.ctime "
            + "<if test='order != null'>"
            + "${order}"
            + "</if>"
            + "</script>")
    List<Post> select_post_all(@Param("order") String order);
    /**
     * 【调用接口】 /post/for-task
     * 【作用】 发布任务攻略贴
     */
    @Insert("insert into posts(creator,title,contain,ctime,likenum,clicktnum,commentnum,task_id) values (#{PostForTaskParams.creator},#{PostForTaskParams.title},#{PostForTaskParams.contain},#{PostForTaskParams.ctime},0,0,0,#{PostForTaskParams.taskid})")
    void insert_post_task(@Param("PostForTaskParams")PostForTaskParams postForTaskParams);
    /**
     * 【调用接口】 /post/normail
     * 【作用】 发布常规贴
     */
    @Insert("insert into posts(creator,title,contain,ctime,likenum,clicktnum,commentnum,task_id) values (#{PostForTaskParams.creator},#{PostForTaskParams.title},#{PostForTaskParams.contain},#{PostForTaskParams.ctime},0,0,0,null)")
    void insert_post_normal(@Param("PostForTaskParams")PostNormalParams params);
    /**
     * 【调用接口】 /post/detail
     * 【作用】 展示选中文章的详情
     * @return 存在返回文章信息
     *         不存在返回  null
     */
    @Select("select users.name,users.img,posts.* from users,posts where posts.id=#{id} and posts.creator=users.id;")
    Post select_post_by_post_id(String id);
    /**
     * 【调用接口】 /post/detail
     * 【作用】 将文章的访问数加1
     */
    @Update("update posts set clicktnum=clicktnum+1 where id=#{id}")
    void update_post_clicktnum_by_id(String id);
    /**
     * 【调用接口】 /post/delete/for-user
     * 【作用】 根据id删除选中文章
     */
    @Delete("delete  from posts where id=#{id}")
    Integer delete_post_by_id(String id);
    /**
     * 【调用接口】 /post/list/for-user
     * 【作用】 根据用户id返回自己发布的文章
     */
    @Select("select users.name,users.img,posts.* from users,posts where posts.creator=#{id} and posts.creator=users.id")
    List<Post> select_post_by_user_id(String id);
    /**
     * 【调用接口】 /post/list/for-keyword
     * 【作用】 根据关键词返回文章
     */
    @Select("select users.name,users.img,posts.* from users,posts where title like concat('%',#{keyWord},'%') and posts.creator=users.id")
    List<Post> select_post_by_key_word(@Param("keyWord")String keyWord);
    /**
     * 【调用接口】 /post/list/for-task
     * 【作用】 返回过关攻略贴
     */
    @Select("select users.name,users.img,posts.* from users,posts where task_id is not null and posts.creator=users.id")
    List<Post> select_post_by_task_id();
    /**
     * 【调用接口】 /post/list/for-usercollect
     * 【作用】 根据用户id返回自己收藏的文章
     */
    @Select("select users.name,users.img,posts.* from users,posts where posts.id in(select post_id from post_collect where user_id=#{userid}) and posts.creator=users.id;")
    List<Post> select_post_collect_by_userid(String userid);
    /**
     * 【调用接口】 /post/like
     * 【作用】 用户给文章点赞
     */
    @Update("update posts set likenum=likenum+1 where id=#{id}")
    void update_post_likenum_by_id(String id);
    /**
     * 【调用接口】 /post/collect
     * 【作用】 用户收藏文章
     */
    @Insert("insert into post_collect(user_id,post_id) values (#{UserId},#{PostId})")
    void insert_post_collect(String UserId,String PostId);
    /**
     * 【调用接口】 /post/collect/delete
     * 【作用】 用户删除收藏文章
     */
    @Delete("delete from post_collect where user_id=#{PostCollectParams.userid} and post_id=#{PostCollectParams.postid};")
    void delete_post_collect_by_id(@Param("PostCollectParams") PostCollectParams params);
    /**
     * 【调用接口】 /post/edit
     * 【作用】 用户编辑发表文章
     */
    @Update("update posts set title=#{PostEditParams.title},contain=#{PostEditParams.contain} where id=#{PostEditParams.postid}")
    Integer update_post_by_id(@Param("PostEditParams") PostEditParams params);
}
