package com.laughbro.welcome.dao.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

@Mapper
public interface CameraMapper {


    @Insert("insert " +
            "into cameralog " +
            "(managerid,locid,taskid,img,time,success) " +
            "valuses " +
            "(#{managerid},#{locid},#{taskid},#{img},#{time},0)")
    @Options(useGeneratedKeys = true, keyProperty = "logid", keyColumn = "logid")
    public int insert_cameralog(String managerid, BigInteger locid, BigInteger taskid, String img, String time);


    @Update("update " +
            "cameralog " +
            "set resultid=#{resultid} " +
            "where logid=#{logid}")
    public void update_cameralog_result(String resultid, BigInteger logid);
}