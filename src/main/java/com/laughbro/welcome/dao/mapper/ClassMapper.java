package com.laughbro.welcome.dao.mapper;

import com.github.pagehelper.Page;
import com.laughbro.welcome.dao.pojo.Class;
import com.laughbro.welcome.vo.params.class_params.ClassParams;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {
    @Insert("insert into school_relate(class_id,grade,campus,college,speciality,class) values (#{id},#{grade},#{campus},#{college},#{speciality},#{class_name})")
    Integer insert_schoolrelate(ClassParams params);

    @Select("select * from school_relate where class_id=#{id}")
    Integer select_schoolrelate_by_classid(String id);

    @Delete("delete from school_relate where class_id=#{id}")
    Integer delete_class_by_id(String id);

    @Update("update school_relate set grade=#{grade},campus=#{campus},college=#{college},speciality=#{speciality},class=#{class_name} where class_id=#{id}")
    Integer update_schoolrelate_by_id(ClassParams params);

    @Select("select * from school_relate where speciality like concat('%',#{keyword},'%')")
    List<Class> select_schoolrelate_by_speciality(String keyword);

    @Select("select * from school_relate")
    List<Class> select_schoolrelate_all();
}
