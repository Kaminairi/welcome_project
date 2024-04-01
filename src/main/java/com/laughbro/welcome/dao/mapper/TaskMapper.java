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

    /**
     * 【调用接口】 /sort/finish-task
     * 【作用】 排行榜
     */
    @Select("SELECT * FROM (" +
            "                  SELECT user_id,users.img,users.name,COUNT(task_fulfillment.user_id) AS passnum" +
            "                  FROM task_fulfillment,users " +
            "                  where user_id=users.id " +
            "                  GROUP BY user_id" +
            "              ) AS subquery" +
            " order by passnum DESC")
    List<TaskSort> selse_task_fulfillment_all();
    /**
     * 【调用接口】 /admin/post/taskset
     * 【作用】 管理员发布任务集合
     */
    @Insert("insert into task_sets(name,applicant,ctime,is_set_due,is_mainline,etime) values(#{TaskSetPostParams.name},#{TaskSetPostParams.applicant},#{TaskSetPostParams.ctime},#{TaskSetPostParams.IsSetDue},#{TaskSetPostParams.IsMainline},#{TaskSetPostParams.etime})")
    Integer insert_taskset(@Param("TaskSetPostParams")TaskSetPostParams params);
    /**
     * 【调用接口】 /admin/get/taskset
     * 【作用】 管理员获取任务集合
     */
    @Select("select * from task_sets")
    List<TaskSet> select_tasksets_all();
    /**
     * 【调用接口】 /admin/post/task
     * 【作用】 管理员发任务
     */
    @Insert("insert into tasks(title,set_id,demand,ctime,btime,dtime,is_mainline,location,example_pic,is_AI,type) values (#{TaskPostParams.title},#{TaskPostParams.setId},#{TaskPostParams.demand},#{TaskPostParams.ctime},#{TaskPostParams.btime},#{TaskPostParams.dtime},#{TaskPostParams.isMainline},#{TaskPostParams.location},#{TaskPostParams.examplePic},#{TaskPostParams.isAI},#{TaskPostParams.type})")
    Integer insert_task(@Param("TaskPostParams")TaskPostParams params);
    /**
     * 【调用接口】 /admin/delete/task
     * 【作用】 管理员删除任务
     */
    @Delete("delete from tasks where id=#{id}")
    Integer delete_task_by_id(String id);
    /**
     * 【调用接口】 /admin/update/task
     * 【作用】 管理员修改任务
     */
    @Update("update tasks set title=#{TaskEditParams.title},set_id=#{TaskEditParams.setId},demand=#{TaskEditParams.demand},ctime=#{TaskEditParams.ctime},btime=#{TaskEditParams.btime},dtime=#{TaskEditParams.dtime},is_mainline=#{TaskEditParams.isMainline},location=#{TaskEditParams.location},example_pic=#{TaskEditParams.examplePic},is_AI=#{TaskEditParams.isAI},type=#{TaskEditParams.type} where id=#{TaskEditParams.id}")
    Integer update_task_by_id(@Param("TaskEditParams")TaskEditParams params);
    /**
     * 【调用接口】 /admin/gettask/bysetid
     * 【作用】 管理员获取集合中的任务
     */
    @Select("select * from tasks where set_id=#{setid}")
    List<Task> select_task_by_set_id_ad(String setid);
    /**
     * 【调用接口】 /admin/gettask/bykeyword
     * 【作用】 管理员搜索任务
     */
    @Select("select * from tasks where title like concat('%',#{keyWord},'%')")
    List<Task> select_task_by_keyword(@Param("keyWord") String keyWord);
    /**
     * 【调用接口】 /admin/check/tasksubmit
     * 【作用】 管理员查看任务提交
     */
    @Select("select * from taskpic where is_pass=0")
    List<TaskPic> select_taskpic_all();
    /**
     * 【调用接口】 /admin/pass/tasksubmit
     * 【作用】 管理员审核通过任务提交
     */
    @Update("update taskpic set is_pass=1 where user_id=#{TaskPicParams.userid} and task_id=#{TaskPicParams.taskid}")
    Integer update_taskpic(@Param("TaskPicParams") TaskPicParams params);
    /**
     * 【调用接口】 /admin/get/taskfulfillment
     * 【作用】 管理员查看完成记录
     */
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
    @Select(" SELECT t.*  " +
            "    FROM `tasks` t " +
            "    WHERE  " +
            "        t.`set_id` = #{id} " +
            "        AND t.`btime` < NOW() " +
            "        AND t.`dtime` > NOW() ")
    List<Task> get_task_by_setid_camera(BigInteger id);
    @Insert("insert into task_fulfillment(user_id,task_id,comp_time) values(#{userid},#{taskid},#{time})")
    void insert_task_fulfillment_camera(String userid,BigInteger taskid,String time);
    @Delete("delete from task_fulfillment where task_id=#{id}")
    Integer delete_task_fulfillment_by_taskid(String id);

    /**
     * 查询是否存在完成记录，用于避免重复完成
     * @param userid
     * @param taskid
     * @return int 查询结果数量
     */
    @Select("Select count(*) " +
            "from task_fulfillment " +
            "where user_id=#{userid} " +
            "and task_id =#{taskid}")
    public int select_countfill_with_id(String userid,BigInteger taskid);
}
