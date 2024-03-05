package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Post;
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
    @Select("select * from posts")
    List<Post> select_post_all();
    /**
     * 【调用接口】 /post/for-task
     * 【作用】 发布任务攻略贴
     */
    @Insert("insert into posts(creator,title,contain,ctime,likenum,clicktnum,commentnum,task_id) values (#{PostForTaskParams.creator},#{PostForTaskParams.title},#{PostForTaskParams.contain},#{PostForTaskParams.ctime},#{PostForTaskParams.likenum},#{PostForTaskParams.clicktnum},#{PostForTaskParams.commentnum},#{PostForTaskParams.TaskId})")
    void insert_post_task(@Param("PostForTaskParams")PostForTaskParams postForTaskParams);
    /**
     * 【调用接口】 /post/normail
     * 【作用】 发布常规贴
     */
    @Insert("insert into posts(creator,title,contain,ctime,likenum,clicktnum,commentnum,task_id) values (#{PostForTaskParams.creator},#{PostForTaskParams.title},#{PostForTaskParams.contain},#{PostForTaskParams.ctime},#{PostForTaskParams.likenum},#{PostForTaskParams.clicktnum},#{PostForTaskParams.commentnum},null)")
    void insert_post_normal(@Param("PostForTaskParams")PostNormalParams params);
    /**
     * 【调用接口】 /post/detail
     * 【作用】 展示选中文章的详情
     * @return 存在返回文章信息
     *         不存在返回  null
     */
    @Select("select * from posts where id=#{id};")
    Post select_post_by_post_id(String id);
    /**
     * 【调用接口】 /post/detail
     * 【作用】 将文章的访问数加1
     */
    @Update("update posts set clicktnum=clicktnum+1 where id=#{id}")
    void update_post_clicktnum_by_id(String id);

    @Delete("delete  from posts where id=#{id}")
    void delete_post_by_id(String id);

    @Select("select * from posts where creator=#{id}")
    List<Post> select_post_by_user_id(String id);

    @Select("select * from posts where title like concat('%',#{keyWord},'%')")
    List<Post> select_post_by_key_word(@Param("keyWord") String keyWord);
}
