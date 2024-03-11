package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;

public interface TaskService {

    Result GetTaskSets(String userid, Integer isMainline, Integer isNow);

    Result GetTasks(String setid,Integer is_now);

    Result GetTask(String taskid);

    Result GetTaskAll(String userid);
}
