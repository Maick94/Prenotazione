package com.example.michele.lavoro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michele on 06/07/2019.
 */


public class Ordinazioni implements Serializable{

    private List<Ordinazione> List;

    public Ordinazioni(List<Ordinazione> List) {
        this.List = List;
    }


    public List<Ordinazione> getOrdinazioniList() {
        return List;
    }

    public void setOrdinazioniList(List<Ordinazione> List) {
        this.List = List;
    }



}