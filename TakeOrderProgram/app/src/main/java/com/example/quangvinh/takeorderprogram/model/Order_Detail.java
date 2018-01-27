package com.example.quangvinh.takeorderprogram.model;

/**
 * Created by QuangVinh on 1/27/2018.
 */

public class Order_Detail {
    int product_id,product_price,product_sl;
    String product_name,img;

    public Order_Detail(int product_id, int product_price, int product_sl, String product_name, String img) {
        this.product_id = product_id;
        this.product_price = product_price;
        this.product_sl = product_sl;
        this.product_name = product_name;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Order_Detail(int product_id, int product_price, int product_sl, String product_name) {
        this.product_id = product_id;
        this.product_price = product_price;
        this.product_sl = product_sl;
        this.product_name = product_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_sl() {
        return product_sl;
    }

    public void setProduct_sl(int product_sl) {
        this.product_sl = product_sl;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
