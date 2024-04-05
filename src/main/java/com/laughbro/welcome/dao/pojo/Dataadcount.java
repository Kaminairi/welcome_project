package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Dataadcount {
    private String date;
    private List<Adcount> adcountList;

    public Dataadcount(String date, List<Adcount> adcountList) {
        this.date = date;
        this.adcountList = adcountList;
    }
}
