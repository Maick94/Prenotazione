package com.example.michele.lavoro.entity;

import java.io.Serializable;

/**
 * Created by Michele on 06/07/2019.
 */

public class Ordinazione implements Serializable {
    private String tipologia;   //primo piatto ecc
    private String nome;
    private int quantita;
    private String extra;

    public Ordinazione(String tipologia, String nome, int quantita, String extra){
        this.tipologia=tipologia;
        this.nome=nome;
        this.quantita = quantita;
        this.extra = extra;
    }


    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


}
