package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Adcount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
    @Select("SELECT" +
            "    ad.id,ad.name, " +
            "    SUM(click) AS total_click, " +
            "    SUM(exposure) AS total_exposure " +
            "FROM " +
            "    `ad_exprosure_record` AS record " +
            "JOIN " +
            "    ad " +
            "ON " +
            "    record.ad_id = ad.id " +
            "WHERE " +
            "    DATE(record.extime) = #{date} " +
            "    AND record.ad_id = #{adid};")
    public List<Adcount> get_cl_ex_by_day_id(String date,String adid);

}
