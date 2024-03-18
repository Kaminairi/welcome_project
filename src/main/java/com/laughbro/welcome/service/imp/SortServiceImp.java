package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.service.SortService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImp implements SortService {
    @Autowired
    private TaskMapper taskMapper;
    public Result GetTaskFInish(){
        return Result.success(taskMapper.selse_task_fulfillment_all());
    }
}
