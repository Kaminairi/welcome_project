package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Advert;
import com.laughbro.welcome.vo.params.advert_params.AddAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.EditAdvertParams;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface AdvertMapper {
    @Insert("insert into ad(name,img,url) values(#{AdvertParams.name},#{AdvertParams.img},#{AdvertParams.url}) ")
    Integer insert_ad(@Param("AdvertParams") AddAdvertParams params);

    @Delete("delete from ad where id=#{id}")
    Integer delete_advert_by_id(String id);

    @Update("update ad set name=#{EditAdvertParams.name},img=#{EditAdvertParams.img},url=#{EditAdvertParams.url} where id=#{EditAdvertParams.id}")
    Integer edit_advert_by_id(@Param("EditAdvertParams") EditAdvertParams params);

    @Select("select * from ad where name like concat('%',#{keyword},'%')")
    List<Advert> select_advert_by_keyword(String keyword);

    @Select("select * from ad")
    List<Advert> select_advert_all();
}
