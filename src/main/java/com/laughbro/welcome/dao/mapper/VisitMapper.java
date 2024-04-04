package com.laughbro.welcome.dao.mapper;

import com.laughbro.welcome.dao.pojo.Visitcount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VisitMapper {


    @Insert("insert into loc_visit " +
            "(loc_id,user_id,vtime) " +
            "values " +
            "(#{loc_id},#{user_id},#{vtime})")
    public int insert_lov_visit(String loc_id,String user_id,String vtime);



    @Select("SELECT l.*, COUNT(lv.loc_id) AS count " +
            "FROM location l " +
            "LEFT JOIN loc_visit lv ON l.id = lv.loc_id AND DATE(lv.vtime) = #{date} " +
            "GROUP BY l.id;")
    public List<Visitcount> select_count_by_date(String date);



}
