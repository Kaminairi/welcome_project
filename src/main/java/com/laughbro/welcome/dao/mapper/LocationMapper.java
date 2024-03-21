package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.vo.params.location_params.LocationAddParams;
import com.laughbro.welcome.vo.params.location_params.LocationEditParams;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

@Mapper
public interface LocationMapper {
    @Insert("insert into location(lng_lat,name,img) values(#{LocationAddParams.lnglat},#{LocationAddParams.name},#{LocationAddParams.img})")
    Integer insert_location(@Param("LocationAddParams") LocationAddParams params);
    @Delete("delete from location where id=#{id}")
    Integer delete_location_by_id(String id);
    @Update("update location set name=#{LocationEditParams.name},img=#{LocationEditParams.img} where id=#{LocationEditParams.id}")
    Integer update_location_by_id(@Param("LocationEditParams") LocationEditParams params);
}
