package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.vo.params.post_params.PostForTaskParams;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

    @Insert("insert into posts(creator,title,contain,ctime,likenum,clicktnum,commentnum,task_id) values (#{PostForTaskParams.creator},#{PostForTaskParams.title},#{PostForTaskParams.contain},#{PostForTaskParams.ctime},#{PostForTaskParams.likenum},#{PostForTaskParams.clicktnum},#{PostForTaskParams.commentnum},#{PostForTaskParams.TaskId})")
    void PostForTask(@Param("PostForTaskParams")PostForTaskParams postForTaskParams);
}
