package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.task_params.*;
import com.laughbro.welcome.vo.params.taskpic_params.TaskPicParams;

public interface TaskService {

    Result GetTaskSets(String userid, Integer isMainline, Integer isNow);

    Result GetTasks(String setid,Integer is_now);

    Result GetTask(String taskid);

    Result GetTaskAll(String userid);

    Result FinishConfirmTask(TaskConfirm taskConfirm);

    Result AdPostTaskSet(TaskSetPostParams params);

    Result AdGetTaskSets();

    Result AdPostTask(TaskPostParams params);

    Result AdDeleteTask(TaskParams params);

    Result AdEditTask(TaskEditParams params);

    Result AdGetTaskBySetId(String setid);

    Result AdGetTaskByKeyword(String keyword);

    Result GetTaskPic();

    Result PassTaskPic(TaskPicParams params);

    Result GetTaskFulfillment();
}
