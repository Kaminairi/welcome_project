package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.TaskSet;
import com.laughbro.welcome.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("call goodwork.sp_get_sets_with_userid(#{userid},#{isMainline},#{isNow})")
    List<TaskSet> select_tasksets(String userid, Integer isMainline, Integer isNow);
    /**
     * 【调用接口】 get/tasks
     * 【作用】 通过任务组id查询全部任务
     */
    @Select("call sp_get_tasks_by_setid(#{id},#{is_now})")
    List<Task> select_task_by_set_id(String id,Integer is_now);
    /**
     * 【调用接口】 get/task
     * 【作用】 通过任务id任务详细信息
     */
    @Select("select * from tasks where id=#{taskid}")
    Task select_task_by_id(String taskid);

    @Select("select * from tasks")
    List<Task> select_task_all();
}
