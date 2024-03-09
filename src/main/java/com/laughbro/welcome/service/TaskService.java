package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;

public interface TaskService {

    Result GetTaskSetMainUnfinish(String userid);


    Result GetTaskSetNmainUnfinish(String userid);


    Result GetTasks(String setid);

    Result GetTask(String taskid);
}
