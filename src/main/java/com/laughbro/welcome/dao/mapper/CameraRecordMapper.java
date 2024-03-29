package com.laughbro.welcome.dao.mapper;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface CameraRecordMapper {

    @Insert("Insert into camerarecord " +
            "(cameratoken, creator, tasklist, ctime, locid) " +
            "values " +
            "(#{cameratoken},#{ creator},#{tasklist},#{ctime},#{locid})")
    public int insert_camrearecord(String cameratoken, String creator, String tasklist, String ctime, BigInteger locid);
}
