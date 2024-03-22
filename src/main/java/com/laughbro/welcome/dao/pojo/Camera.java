package com.laughbro.welcome.dao.pojo;

import com.laughbro.welcome.dao.mapper.BagMapper;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class Camera {
    private String managerid;//相机登陆的管理员id
    private BigInteger locid;//相机部署的位置id
    private List<String> tasklist;//相机对应的任务数组

    public Camera(String managerid) {
        this.managerid = managerid;
    }

    public Camera(String managerid, BigInteger locid) {
        this.managerid = managerid;
        this.locid = locid;
    }
}
