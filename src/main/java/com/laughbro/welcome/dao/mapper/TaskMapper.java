package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Select("select * from tasks")
    List<Task> select_task_all();

}
