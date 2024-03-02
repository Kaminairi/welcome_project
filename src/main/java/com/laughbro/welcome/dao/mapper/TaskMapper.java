package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Task;
import com.laughbro.welcome.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    /**
     * 【调用接口】 quest/main
     * 【作用】 查询主线任务信息
     * @param
     * @return 存在返回 所有主线任务
     *         不存在返回  null
     */
    @Select("select * from tasks where is_mainline=1")
    List<Task> select_task_main();

    /**
     * 【调用接口】 quest/nmain
     * 【作用】 查询支线任务信息
     * @param
     * @return 存在返回 所有支线任务
     *         不存在返回  null
     */
    @Select("select * from tasks where is_mainline!=1")
    List<Task> select_task_nmain();

    /**
     * 【调用接口】 quest/finish
     * 【作用】 通过id查询用户已完成任务
     * @param
     * @return 存在返回 完成的所有任务
     *         不存在返回  null
     */
    @Select("select * from tasks where id in (select task_id from task_fulfillment where user_id=#{id})")
    List<Task> select_task_finish(String id);
}
