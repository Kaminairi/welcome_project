package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Select("select * from tasks where is_mainline=1")
    List<Task> select_task_main();

    @Select("select * from tasks where is_mainline!=1")
    List<Task> select_task_nmain();

    @Select("select * from tasks where id in (select task_id from task_fulfillment where user_id=#{id})")
    List<Task> select_task_finish(String id);
}
