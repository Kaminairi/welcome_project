package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Location;
import com.laughbro.welcome.vo.params.location_params.LocationAddParams;
import com.laughbro.welcome.vo.params.location_params.LocationEditParams;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface LocationMapper {
    /**
     * 【调用接口】 /admin/add/location
     * 【作用】 管理员新增点位
     */
    @Insert("insert into location(lng_lat,name,img) values(#{LocationAddParams.lnglat},#{LocationAddParams.name},#{LocationAddParams.img})")
    Integer insert_location(@Param("LocationAddParams") LocationAddParams params);
    /**
     * 【调用接口】 /admin/delete/location
     * 【作用】 管理员删除点位
     */
    @Delete("delete from location where id=#{id}")
    Integer delete_location_by_id(String id);
    /**
     * 【调用接口】 /admin/edit/location
     * 【作用】 管理员修改点位
     */
    @Update("update location set name=#{LocationEditParams.name},img=#{LocationEditParams.img} where id=#{LocationEditParams.id}")
    Integer update_location_by_id(@Param("LocationEditParams") LocationEditParams params);
    /**
     * 【调用接口】 /admin/get/locationall
     * 【作用】 管理员查看全部点位
     */
    @Select("select * from location")
    List<Location> select_location_all();


}
