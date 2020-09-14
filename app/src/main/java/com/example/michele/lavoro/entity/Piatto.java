package com.example.michele.lavoro.entity;

import android.text.Editable;

import java.io.Serializable;

/**
 * Created by Michele on 03/07/2019.
 */

public class Piatto implements Serializable {
    private String image;
    private String tipologia;   //primo piatto ecc
    private String nome;
    private String prezzo;      //dividiamo le stringhe e le facciamo numeriche



    public Piatto(String image, String tipologia,  String nome, String prezzo) {
        this.image = image;
        this.tipologia = tipologia;
        this.prezzo = prezzo;
        this.nome = nome;
    }




    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
