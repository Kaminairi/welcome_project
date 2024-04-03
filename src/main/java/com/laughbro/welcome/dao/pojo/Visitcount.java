package com.laughbro.welcome.dao.pojo;

import com.laughbro.welcome.dao.mapper.BagMapper;
import lombok.Data;

import java.math.BigInteger;
@Data
public class Visitcount {
    private  String id;
   private  BigInteger count;
    private String lnglat;
    private String name;
    private String img;
}
