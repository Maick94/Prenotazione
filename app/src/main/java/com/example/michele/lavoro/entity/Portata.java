package com.example.michele.lavoro.entity;

/**
 * Created by Michele on 03/07/2019.
 */

import java.io.Serializable;

public class Portata implements Serializable {
    private String nomeTavolo;
    private String ordinazioni;


    public Portata(String nomeTavolo,String ordinazioni){
        this.nomeTavolo=nomeTavolo;
        this.ordinazioni=ordinazioni;
    }


    public String getNomeTavolo() {
        return nomeTavolo;
    }

    public void setNomeTavolo(String nomeTavolo) {
        this.nomeTavolo = nomeTavolo;
    }

    public String getOrdinazioni() {
        return ordinazioni;
    }

    public void setOrdinazioni(String ordinazioni) {
        this.ordinazioni = ordinazioni;
    }
}
