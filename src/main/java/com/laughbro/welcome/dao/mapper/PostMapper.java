package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Post;
import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import com.laughbro.welcome.vo.params.post_params.PostNormalParams;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select * from posts")
    List<Post> select_post_all();
    @Insert("insert into posts(creator,title,contain,ctime,likenum,clicktnum,commentnum,task_id) values (#{PostForTaskParams.creator},#{PostForTaskParams.title},#{PostForTaskParams.contain},#{PostForTaskParams.ctime},#{PostForTaskParams.likenum},#{PostForTaskParams.clicktnum},#{PostForTaskParams.commentnum},#{PostForTaskParams.TaskId})")
    void insert_post_task(@Param("PostForTaskParams")PostForTaskParams postForTaskParams);

    @Insert("insert into posts(creator,title,contain,ctime,likenum,clicktnum,commentnum,task_id) values (#{PostForTaskParams.creator},#{PostForTaskParams.title},#{PostForTaskParams.contain},#{PostForTaskParams.ctime},#{PostForTaskParams.likenum},#{PostForTaskParams.clicktnum},#{PostForTaskParams.commentnum},null)")
    void insert_post_normal(@Param("PostForTaskParams")PostNormalParams params);

    @Select("select * from posts where id=#{id};")
    Post select_post_by_id(String id);

    @Update("update posts set clicktnum=clicktnum+1 where id=#{id}")
    void update_post_clicktnum_by_id(String id);
}
