package com.laughbro.welcome.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.laughbro.welcome.dao.mapper.ClassMapper;
import com.laughbro.welcome.dao.pojo.Class;
import com.laughbro.welcome.service.ClassService;
import com.laughbro.welcome.vo.PageResult;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.class_params.ClassParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImp implements ClassService {
    @Autowired
    private ClassMapper classMapper;
    @Override
    public Result AddClass(ClassParams params){
        if(classMapper.select_schoolrelate_by_classid(params.getId())==null){
            classMapper.insert_schoolrelate(params);
            return Result.success("新增班级成功");
        }else{
            return Result.fail(100,"fail","班级已存在");
        }
    }

    @Override
    public Result DeleteClass(String id){
        if(classMapper.delete_class_by_id(id)==1){
            return Result.success("删除成功");
        }else{
            return Result.fail(100,"fail","删除失败");
        }
    }

    @Override
    public Result EditClass(ClassParams params){
        if(classMapper.update_schoolrelate_by_id(params)==1){
            return Result.success("修改成功");
        }else{
            return Result.fail(100,"fail","修改失败");
        }
    }

    @Override
    public Result SearchClass(int page,int pagesize,String keyword) {
        PageHelper.startPage(page,pagesize);
        Page<Class> p=(Page<Class>) classMapper.select_schoolrelate_by_speciality(keyword);
        if(p.isEmpty()){
            return Result.success("还没有相关数据");
        }else{
            return PageResult.success(p,p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1);
        }
    }

    @Override
    public Result GetClass(int page,int pagesize){
        PageHelper.startPage(page,pagesize);
        Page<Class> p=(Page<Class>) classMapper.select_schoolrelate_all();
        if(p.isEmpty()){
            return Result.success("还没有相关数据");
        }else{

            return PageResult.success(p,p.getTotal()%pagesize==0?p.getTotal()/pagesize:p.getTotal()/pagesize+1);
        }
    }
}
