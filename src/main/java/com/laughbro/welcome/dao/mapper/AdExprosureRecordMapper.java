package com.laughbro.welcome.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper

public interface AdExprosureRecordMapper {
    //创建曝光记录
    @Insert("INSERT INTO `ad_exprosure_record` " +
            "(`user_id`,`ad_id`,`extime`) " +
            "VALUES " +
            "(#{userid},#{ad_id},#{extime})")
    public int insert_adex(String userid, String ad_id, String extime);


    //改变点击记录
    @Update("UPDATE `ad_exprosure_record` " +
            "SET`click`=1,`" +
            "cltime`=#{cltime} " +
            "WHERE user_id=#{userid} " +
            "and extime=#{extime} " +
            "and ad_id=#{ad_id}")
    public int update_adex_cl(String userid,String cltime,String extime,String ad_id);


    //获得所有任务的曝光转化率
}
