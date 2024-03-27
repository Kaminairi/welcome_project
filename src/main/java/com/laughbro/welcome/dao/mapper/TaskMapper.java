package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.*;
import com.laughbro.welcome.vo.params.task_params.*;
import com.laughbro.welcome.vo.params.taskpic_params.TaskPicParams;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
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
    @Select("call sp_get_tasks_by_setid(#{id},#{userid},#{is_now})")
    List<Task> select_task_by_set_id(String id,String userid,Integer is_now);
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

    @Select("SELECT * FROM (" +
            "                  SELECT user_id,users.img,users.name,COUNT(task_fulfillment.user_id) AS passnum" +
            "                  FROM task_fulfillment,users " +
            "                  where user_id=users.id " +
            "                  GROUP BY user_id" +
            "              ) AS subquery" +
            " order by passnum DESC")
    List<TaskSort> selse_task_fulfillment_all();
    @Insert("insert into task_sets(name,applicant,ctime,is_set_due,is_mainline,etime) values(#{TaskSetPostParams.name},#{TaskSetPostParams.applicant},#{TaskSetPostParams.ctime},#{TaskSetPostParams.IsSetDue},#{TaskSetPostParams.IsMainline},#{TaskSetPostParams.etime})")
    Integer insert_taskset(@Param("TaskSetPostParams")TaskSetPostParams params);

    @Select("select * from task_sets")
    List<TaskSet> select_tasksets_all();
    @Insert("insert into tasks(title,set_id,demand,ctime,btime,dtime,is_mainline,location,example_pic,is_AI,type) values (#{TaskPostParams.title},#{TaskPostParams.setId},#{TaskPostParams.demand},#{TaskPostParams.ctime},#{TaskPostParams.btime},#{TaskPostParams.dtime},#{TaskPostParams.isMainline},#{TaskPostParams.location},#{TaskPostParams.examplePic},#{TaskPostParams.isAI},#{TaskPostParams.type})")
    Integer insert_task(@Param("TaskPostParams")TaskPostParams params);
    @Delete("delete from tasks where id=#{id}")
    Integer delete_task_by_id(String id);
    @Update("update tasks set title=#{TaskEditParams.title},set_id=#{TaskEditParams.setId},demand=#{TaskEditParams.demand},ctime=#{TaskEditParams.ctime},btime=#{TaskEditParams.btime},dtime=#{TaskEditParams.dtime},is_mainline=#{TaskEditParams.isMainline},location=#{TaskEditParams.location},example_pic=#{TaskEditParams.examplePic},is_AI=#{TaskEditParams.isAI},type=#{TaskEditParams.type} where id=#{TaskEditParams.id}")
    Integer update_task_by_id(@Param("TaskEditParams")TaskEditParams params);

    @Select("select * from tasks where set_id=#{setid}")
    List<Task> select_task_by_set_id_ad(String setid);
    @Select("select * from tasks where title like concat('%',#{keyWord},'%')")
    List<Task> select_task_by_keyword(@Param("keyWord") String keyWord);

    @Select("select * from taskpic")
    List<TaskPic> select_taskpic_all();
    @Update("update taskpic set is_pass=1 where user_id=#{TaskPicParams.userid} and task_id=#{TaskPicParams.taskid}")
    Integer update_taskpic(@Param("TaskPicParams") TaskPicParams params);

    @Select("select * from task_fulfillment order by comp_time desc")
    List<TaskFulfillment> select_task_fulfillment_all();


    ////根据管理员的账号获得其拥有权限的任务集合
    @Select("SELECT DISTINCT s.* " +
            "FROM task_sets s " +
            "JOIN tasks t ON s.set_id = t.set_id " +
            "WHERE s.applicant = #{id} " +
            "AND t.location = #{locid} " +
            "AND t.type LIKE '人脸打卡' " +
            "AND t.btime<NOW() " +
            "AND t.dtime>NOW()"
            )
    List<TaskSet> get_set_by_managerid_locid_camera(String id, String locid);

    //根据管理员给的setid获得任务，要没有过期，要有摄像头
    @Select("call sp_get_tasks_by_setid(#{id},#{is_now}) ")
    List<Task> get_task_by_setid_camera(BigInteger id, int is_now);
}
