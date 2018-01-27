package com.example.quangvinh.takeorderprogram.util;

/**
 * Created by QuangVinh on 1/25/2018.
 */

public class Server {
    public static String localhost = "192.168.0.101";
    public static String url = "http://"+localhost+"/server/get_product_type.php";
    public  static String type_add = "http://"+localhost+"/server/insert_product_type.php";
    public  static String type_edit = "http://"+localhost+"/server/update_product_type.php";
    public  static String type_delete  = "http://"+localhost+"/server/delete_product_type.php";
    public  static String table_data = "http://"+localhost+"/server/get_table_data.php";
    public static String table_add = "http://"+localhost+"/server/insert_table.php";
    public static String table_edit = "http://"+localhost+"/server/update_table.php";
    public static String table_delete = "http://"+localhost+"/server/dete_table.php";
    public static String product_get = "http://"+localhost+"/server/get_product_con.php";
    public static String product_add = "http://"+localhost+"/server/insert_product.php";
    public static String order_add = "http://"+localhost+"/server/insert_order.php";
    public static String order_get ="http://"+localhost+"/server/get_order.php";


}
