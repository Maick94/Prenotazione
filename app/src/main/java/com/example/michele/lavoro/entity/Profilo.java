package com.example.michele.lavoro.entity;

/**
 * Created by Michele on 03/07/2019.
 */

import java.io.Serializable;

public class Profilo implements Serializable {
    private String nickname;
    private String password;


    public Profilo(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;

    }

    /*public Profilo(String nickname) {
        this.nickname = nickname;
    }*/

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
