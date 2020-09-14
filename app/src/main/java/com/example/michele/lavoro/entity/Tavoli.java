package com.example.michele.lavoro.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michele on 16/07/2019.
 */

public class Tavoli implements Serializable {
    private List<Tavolo> tavoloList;

    public Tavoli(List<Tavolo> tavoloList) {
        this.tavoloList = tavoloList;
    }

    public List<Tavolo> getTavoloList() {
        return tavoloList;
    }

    public void setTavoloList(List<Tavolo> tavoloList) {
        this.tavoloList = tavoloList;
    }

}
