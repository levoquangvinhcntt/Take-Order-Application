package com.example.quangvinh.takeorderprogram.model;

/**
 * Created by QuangVinh on 1/26/2018.
 */

public class Product {
     private int product_id,product_price;
     private String product_name,product_type;
     private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Product(int product_id, int product_price, String product_name, String product_type, String img) {
        this.product_id = product_id;
        this.product_price = product_price;
        this.product_name = product_name;
        this.product_type = product_type;
        this.img = img;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
