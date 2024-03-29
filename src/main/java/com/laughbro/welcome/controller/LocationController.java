package com.laughbro.welcome.controller;

import com.laughbro.welcome.service.LocationService;
import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.location_params.LocationAddParams;
import com.laughbro.welcome.vo.params.location_params.LocationDeleteParams;
import com.laughbro.welcome.vo.params.location_params.LocationEditParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;
    /**
     * 【作用】 管理员新增点位
     */
    @RequestMapping("/admin/add/location")
    public Result AddLocation(@RequestBody LocationAddParams params){
        return locationService.AddLocation(params);
    }
    /**
     * 【作用】 管理员删除点位
     */
    @RequestMapping("/admin/delete/location")
    public Result DeleteLocation(@RequestBody LocationDeleteParams params){
        return locationService.DeleteLocation(params);
    }
    /**
     * 【作用】 管理员编辑点位
     */
    @RequestMapping("/admin/edit/location")
    public Result EditLocation(@RequestBody LocationEditParams params){
        return locationService.EditLocation(params);
    }
    /**
     * 【作用】 管理员查看所有点位
     */
    @RequestMapping("/admin/get/locationall")
    public Result GetLocationAll(){
        return locationService.GetLocationAll();
    }
}
