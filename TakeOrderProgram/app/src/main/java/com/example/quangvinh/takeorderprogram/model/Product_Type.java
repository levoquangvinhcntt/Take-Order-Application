package com.example.quangvinh.takeorderprogram.model;

/**
 * Created by QuangVinh on 1/25/2018.
 */

public class Product_Type {
    private int type_id;
    private String name,img;

    public Product_Type(int type_id, String name, String img) {
        this.type_id = type_id;
        this.name = name;
        this.img = img;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType_id() {
        return type_id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }
}
