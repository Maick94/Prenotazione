package com.example.michele.lavoro.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michele on 21/07/2019.
 */

public class Portate implements Serializable {

    private java.util.List<Portata> List;

    public Portate(List<Portata> List) {
        this.List = List;
    }


    public List<Portata> getPortateList() {
        return List;
    }

    public void setPortateList(List<Portata> List) {
        this.List = List;
    }

}