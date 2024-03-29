package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.LocationMapper;
import com.laughbro.welcome.dao.pojo.Location;
import com.laughbro.welcome.service.LocationService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.location_params.LocationAddParams;
import com.laughbro.welcome.vo.params.location_params.LocationDeleteParams;
import com.laughbro.welcome.vo.params.location_params.LocationEditParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImp implements LocationService {
    @Autowired
    private LocationMapper locationMapper;
    /**
     * 【作用】 管理员新增点位
     */
    @Override
    public Result AddLocation(LocationAddParams params){
        try {
            if (locationMapper.insert_location(params) == 1) {
                return Result.success("点位增加成功");
            } else {
                return Result.fail(100, "fail", "点位增加失败");
            }
        }catch (Exception e){
            return Result.fail(100,"fail","点位增加失败");
        }
    }
    /**
     * 【作用】 管理员删除点位
     */
    @Override
    public Result DeleteLocation(LocationDeleteParams params){
        if(locationMapper.delete_location_by_id(params.getId())==1){
            return Result.success("删除成功");
        }else{
            return Result.fail(100,"fail","删除失败");
        }
    }
    /**
     * 【作用】 管理员修改点位
     */
    @Override
    public Result EditLocation(LocationEditParams params){
        try{
            if(locationMapper.update_location_by_id(params)==1){
                return Result.success("修改成功");
            }else{
                return Result.fail(100,"fail","修改失败");
            }
        }catch (Exception e){
            return Result.fail(100,"fail","修改失败");
        }

    }
    /**
     * 【作用】 管理员查看全部点位
     */
    @Override
    public Result GetLocationAll(){
        List<Location> list=locationMapper.select_location_all();
        if(list.isEmpty()){
            return Result.success("还没有设置点位");
        }else{
            return Result.success(list);
        }
    }


}
