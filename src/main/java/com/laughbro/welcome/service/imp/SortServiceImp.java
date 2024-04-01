package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.BagMapper;
import com.laughbro.welcome.dao.mapper.TaskMapper;
import com.laughbro.welcome.dao.pojo.ScoreSort;
import com.laughbro.welcome.dao.pojo.TaskFinishSort;
import com.laughbro.welcome.service.SortService;
import com.laughbro.welcome.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortServiceImp implements SortService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private BagMapper bagMapper;
    @Override
    public Result GetTaskFInish(){
        List<TaskFinishSort> list=taskMapper.selse_task_fulfillment_all();
        if(list.isEmpty()){
            return Result.success("暂时没有数据");
        }else{
            return Result.success(list);
        }
    }
    @Override
    public Result GetScore(){
        List<ScoreSort> list=bagMapper.select_score_all();
        if(list.isEmpty()){
            return Result.success("暂时没有数据");
        }else{
            return Result.success(list);
        }
    }

}
