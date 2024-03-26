package com.laughbro.welcome.dao.mapper;


import com.laughbro.welcome.dao.pojo.Manager;
import com.laughbro.welcome.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ManagerMapper {


    //查询登录

    @Select("select * " +
            "from managers " +
            "where id=#{id}")
    public Manager select_manager_all_by_id(String id);


    //
    @Update("update managers  " +
            "set pwd=#{pwd} " +
            "where id like #{id} ")
    public void update_manager_pwd_by_id(String pwd, String id);





}
