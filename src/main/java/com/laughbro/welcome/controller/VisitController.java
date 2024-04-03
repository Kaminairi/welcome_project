package com.laughbro.welcome.controller;

import com.laughbro.welcome.dao.mapper.VisitMapper;
import com.laughbro.welcome.dao.pojo.Visitcount;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.VisitParams;
import com.laughbro.welcome.vo.params.user_params.UserDeleteParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VisitController {

    @Autowired
    private TimeUtils timeUtils;
    @Autowired
    private VisitMapper visitMapper;
    //增加记录
    @PostMapping("/data/visit/add")
    public Result visitadd(@RequestBody VisitParams visitParams){
        int num=visitMapper.insert_lov_visit(visitParams.getLoc_id(),visitParams.getUser_id(),timeUtils.timeGetNow());
        return Result.success(num);
    }


    //所有记录 日
    @GetMapping("/data/visit/get_n_days")
    public Result visitgetweek(int day){
        List<Visitcount> week=visitMapper.select_count_by_days(day);
        return Result.success(week);
    }

    //所有记录 周


    //所有记录 月




    //某个点位的当日频率

    //某个点位的



}
