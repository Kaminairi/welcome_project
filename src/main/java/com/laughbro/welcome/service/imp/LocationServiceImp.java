package com.laughbro.welcome.service.imp;

import com.laughbro.welcome.dao.mapper.LocationMapper;
import com.laughbro.welcome.service.LocationService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.location_params.LocationAddParams;
import com.laughbro.welcome.vo.params.location_params.LocationDeleteParams;
import com.laughbro.welcome.vo.params.location_params.LocationEditParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImp implements LocationService {
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public Result AddLocation(LocationAddParams params){
        return Result.success(locationMapper.insert_location(params));
    }

    @Override
    public Result DeleteLocation(LocationDeleteParams params){
        return Result.success(locationMapper.delete_location_by_id(params.getId()));
    }

    @Override
    public Result EditLocation(LocationEditParams params){
        return Result.success(locationMapper.update_location_by_id(params));
    }

}
