package com.example.michele.lavoro.entity;

import java.io.Serializable;

/**
 * Created by Michele on 14/07/2019.
 */

public class Tavolo implements Serializable {
    private String nome;
    private int num_posti;
    private int num_posti_occupati;

    public Tavolo(String nome, int num_posti, int num_posti_occupati){
        this.nome=nome;
        this.num_posti=num_posti;
        this.num_posti_occupati=num_posti_occupati;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNum_posti() {
        return num_posti;
    }

    public void setNum_posti(int num_posti) {
        this.num_posti = num_posti;
    }

    public int getNum_posti_occupati() {
        return num_posti_occupati;
    }

    public void setNum_posti_occupati(int num_posti_occupati) {
        this.num_posti_occupati = num_posti_occupati;
    }
}
