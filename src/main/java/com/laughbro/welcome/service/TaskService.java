package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.*;
import com.laughbro.welcome.vo.params.taskpic_params.TaskPicParams;

public interface TaskService {

    Result GetTaskSets(String userid, Integer isMainline, Integer isNow);

    Result GetTasks(String setid,String userid,Integer is_now);

    Result GetTask(String taskid);

    Result GetTaskAll(String userid);

    Result FinishConfirmTask(TaskConfirm taskConfirm);

    Result AdPostTaskSet(TaskSetPostParams params);

    Result AdGetTaskSets(int page,int pagesize);

    Result AdPostTask(TaskPostParams params);

    Result AdDeleteTask(TaskParams params);

    Result AdEditTask(TaskEditParams params);

    Result AdGetTaskBySetId(int page,int pagesize,String setid);

    Result AdGetTaskByKeyword(String keyword);

    Result GetTaskPic(int page,int pagesize);

    Result PassTaskPic(TaskPicParams params);

    Result GetTaskFulfillment(int page,int pagesize);

    Result RefuseTaskPic(TaskPicParams params);

    void GetReward(String userid, String taskid);
}
