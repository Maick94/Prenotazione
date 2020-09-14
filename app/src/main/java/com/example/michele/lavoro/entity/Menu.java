package com.example.michele.lavoro.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michele on 05/07/2019.
 */

public class Menu implements Serializable {
    private List<Piatto> piattoList;

    public Menu(List<Piatto> piattoList) {
        this.piattoList = piattoList;
    }

    public List<Piatto> getMenuList() {
        return piattoList;
    }

    public void setMenuList(List<Piatto> piattoList) {
        this.piattoList = piattoList;
    }
}
