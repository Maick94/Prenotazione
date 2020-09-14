package com.example.michele.lavoro.adapters;

/**
 * Created by Michele on 04/07/2019.
 */

public class ItemDataSpinner {

    String text;
    Integer imageId;

    public ItemDataSpinner(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }
}
