package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.TaskFulFillment;
import com.laughbro.welcome.dao.pojo.TaskSet;
import com.laughbro.welcome.vo.params.task_params.TaskConfirm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    /**
     * 【调用接口】 /get/tasksets
     * 【作用】 获取任务集合
     */
    @Select("call goodwork.sp_get_sets_with_userid(#{userid},#{isMainline},#{isNow})")
    List<TaskSet> select_tasksets(String userid, Integer isMainline, Integer isNow);
    /**
     * 【调用接口】 /get/tasks
     * 【作用】 通过任务组id查询全部任务
     */
    @Select("call sp_get_tasks_by_setid(#{id},#{is_now})")
    List<Task> select_task_by_set_id(String id,Integer is_now);
    /**
     * 【调用接口】 /get/task
     * 【作用】 通过任务id任务详细信息
     */
    @Select("select * from tasks inner join location on tasks.location=location.id where tasks.id=#{taskid}")
    Task select_task_by_id(String taskid);
    /**
     * 【调用接口】 /get/task-all
     * 【作用】 获取所有任务
     */
    @Select("call sp_get_unfinish_task_by_userid(#{userid})")
    List<Task> select_task_all(String userid);
    /**
     * 【调用接口】 /task/confirm
     * 【作用】 完成公告任务
     */
    @Insert("insert into task_fulfillment(user_id,task_id,comp_time) values(#{TaskConfirm.userid},#{TaskConfirm.taskid},#{TaskConfirm.time})")
    void insert_task_fulfillment(@Param("TaskConfirm")TaskConfirm taskConfirm);

    @Select("select user_id,users.name,users.img,count(user_id) as passnum from task_fulfillment,users where user_id=users.id;")
    List<TaskFulFillment> selse_task_fulfillment_all();
}
