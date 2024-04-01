package com.laughbro.welcome.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.python.modules._imp;

import java.math.BigInteger;

@Mapper
public interface CameraLogMapper {

    @Insert("INSERT INTO `cameralog` " +
            "(`managerid`,`locid`,`taskid`,`img`,`time`,`resultid`,`success`) " +
            "VALUES " +
            "(#{managerid},#{locid},#{taskid},#{img},#{time},#{resultid},#{success})")
    public int insert_cameralog_add(String managerid, BigInteger locid ,BigInteger taskid ,String img ,String time ,String resultid,int success);

    @Update("update cameralog " +
            "set success =#{success} " +
            "where `managerid`=#{managerid} " +
            "and `locid`=#{locid} " +
            "and `taskid`=#{taskid} " +
            "and `img`=#{img}")
    public int update_cameralog_success(String managerid, BigInteger locid ,BigInteger taskid ,String img ,int success);
}
