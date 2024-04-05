package com.laughbro.welcome.controller;

import com.laughbro.welcome.dao.mapper.AdExprosureRecordMapper;
import com.laughbro.welcome.dao.mapper.VisitMapper;
import com.laughbro.welcome.dao.pojo.Adcount;
import com.laughbro.welcome.dao.pojo.Dataadcount;
import com.laughbro.welcome.dao.pojo.Datevistcount;
import com.laughbro.welcome.dao.pojo.Visitcount;
import com.laughbro.welcome.utils.TimeUtils;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.VisitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Datacontroller {

    @Autowired
    private TimeUtils timeUtils;
    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private AdExprosureRecordMapper adExprosureRecordMapper;
    //增加记录
    @PostMapping("/data/visit/add")
    public Result visitadd(@RequestBody VisitParams visitParams){
        int num=visitMapper.insert_lov_visit(visitParams.getLoc_id(),visitParams.getUser_id(),timeUtils.timeGetNow());
        return Result.success(num);
    }


    //所有记录 日
    @GetMapping("/data/visit/get_n_days")
    public Result visitgebetweenday(int bday, int eday){
        //获得日期
        List<String> datelist=timeUtils.getDateRange(bday,eday);
        List<Datevistcount> datevistcounts=new ArrayList<>();
        for (String date : datelist) {
            List<Visitcount> daycount=visitMapper.select_count_by_date(date);
            datevistcounts.add(new Datevistcount(date,daycount));
        }

        return Result.success(datevistcounts);
    }

    @GetMapping("/data/ad/get_n_days")
    public Result adgetbetweenday(int bday, int eday,String adid){
        //获得日期
        List<String> datelist=timeUtils.getDateRange(bday,eday);
        List<Dataadcount> dataadcounts=new ArrayList<>();
        for (String date : datelist) {
            List<Adcount> daycount=adExprosureRecordMapper.get_cl_ex_by_day_id(date,adid);
            dataadcounts.add(new Dataadcount(date,daycount));
        }

        return Result.success(dataadcounts);
    }




}
