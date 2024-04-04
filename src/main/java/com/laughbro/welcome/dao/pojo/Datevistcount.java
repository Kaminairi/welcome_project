package com.laughbro.welcome.dao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Datevistcount {

    private  String  date;
    private List<Visitcount> visitcounts;

    public Datevistcount(String date, List<Visitcount> visitcounts) {
        this.date = date;
        this.visitcounts = visitcounts;
    }
}
