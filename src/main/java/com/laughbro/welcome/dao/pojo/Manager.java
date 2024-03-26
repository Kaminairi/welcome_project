package com.laughbro.welcome.dao.pojo;

import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Data
public class Manager {
    private String id;
    private String name;
    private String jobid;
    private String email;
    private String tel;
    private String img;
    private String pwd;
    private String cameratoken;
    private List<Location> locationList;

}
