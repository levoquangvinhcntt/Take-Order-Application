package com.example.quangvinh.takeorderprogram.model;

/**
 * Created by QuangVinh on 1/26/2018.
 */

public class Table {
    int table_id;String table_name;

    public Table(int table_id, String table_name) {
        this.table_id = table_id;
        this.table_name = table_name;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
