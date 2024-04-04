package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Advert;
import com.laughbro.welcome.vo.params.advert_params.AddAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.EditAdvertParams;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdvertMapper {
    /**
     * 【调用接口】 /admin/add/advert
     * 【作用】 管理员发布广告
     */
    @Insert("insert into ad(name,img,url) values(#{AdvertParams.name},#{AdvertParams.img},#{AdvertParams.url}) ")
    Integer insert_ad(@Param("AdvertParams") AddAdvertParams params);
    /**
     * 【调用接口】 /admin/delete/advert
     * 【作用】 管理员删除广告
     */
    @Delete("delete from ad where id=#{id}")
    Integer delete_advert_by_id(String id);
    /**
     * 【调用接口】 /admin/edit/advert
     * 【作用】 管理员修改广告
     */
    @Update("update ad set name=#{EditAdvertParams.name},img=#{EditAdvertParams.img},url=#{EditAdvertParams.url} where id=#{EditAdvertParams.id}")
    Integer update_advert_by_id(@Param("EditAdvertParams") EditAdvertParams params);
    /**
     * 【调用接口】 /admin/get/advert
     * 【作用】 管理员搜索广告
     */
    @Select("select * from ad where name like concat('%',#{keyword},'%')")
    List<Advert> select_advert_by_keyword(String keyword);
    /**
     * 【调用接口】 /admin/get/advertall
     * 【作用】 管理员查看全部广告
     */
    @Select("select * from ad")
    List<Advert> select_advert_all();

    @Select("select * from ad where id=#{id}")
    Advert select_advert_by_id(String id);

    @Update("update ad set click_num=click_num+1 where id=#{id}")
    void update_advert_clicknum_by_id(String id);

    @Select("SELECT * FROM ad ORDER BY RAND() LIMIT 2")
    List<Advert> select_ad_limit2_rand();
}
