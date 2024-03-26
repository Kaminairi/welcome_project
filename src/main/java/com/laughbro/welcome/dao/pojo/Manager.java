package com.laughbro.welcome.dao.pojo;

import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
public class Manager {
    private String id;
    private String name;
    private String jobid;
    private String email;
    private String tel;
    private String img;

}
