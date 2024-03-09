package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.TaskSet;
import com.laughbro.welcome.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    /**
     * 【调用接口】 /gettasksets/main/unfinish
     * 【作用】 查询主线任务集合
     */
    @Select("call sp_get_sets_with_userid(#{id},#{is_mainline},#{is_now})")
    List<TaskSet> select_taskset_main_unfinish(String id, Integer is_mainline, Integer is_now);

    /**
     * 【调用接口】 /gettasksets/nmain/unfinish
     * 【作用】 查询支线任务集合
     */
    @Select("call sp_get_sets_with_userid(#{id},#{is_mainline},#{is_now})")
    List<TaskSet> select_taskset_nmain_unfinish(String id,Integer is_mainline,Integer is_now);

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
}
