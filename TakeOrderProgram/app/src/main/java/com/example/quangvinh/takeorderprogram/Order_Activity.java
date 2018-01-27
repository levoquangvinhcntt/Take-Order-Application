package com.example.quangvinh.takeorderprogram;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quangvinh.takeorderprogram.adapter.Combobox_Adapter;
import com.example.quangvinh.takeorderprogram.adapter.Order_Detail_Adapter;
import com.example.quangvinh.takeorderprogram.adapter.Product_Adapter;
import com.example.quangvinh.takeorderprogram.model.Order_Detail;
import com.example.quangvinh.takeorderprogram.model.Product;
import com.example.quangvinh.takeorderprogram.util.Check_Connect;
import com.example.quangvinh.takeorderprogram.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order_Activity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    ArrayList<Product> array_product;
    Product_Adapter product_adapter;
    GridView grid_product;
    TextView lb_price;
    ArrayList<Order_Detail> array_detail;
    Order_Detail_Adapter detail_adapter;
    ListView list_detail;
    TextView txt_khachhang;

    ArrayList<String> array_table;
    Combobox_Adapter table_adapter;
    String table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        Mapping();
    }

    private void Mapping(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        array_product = new ArrayList<Product>();
        product_adapter = new Product_Adapter(getApplicationContext(),array_product,R.layout.product_item);
        grid_product = (GridView)findViewById(R.id.grid_product);
        grid_product.setAdapter(product_adapter);
        getData_Product();
        array_detail = new ArrayList<Order_Detail>();
        detail_adapter = new Order_Detail_Adapter(this,getApplicationContext(),array_detail,R.layout.order_detail);
        list_detail = (ListView)findViewById(R.id.list_detail);
        list_detail.setAdapter(detail_adapter);
        lb_price = (TextView)findViewById(R.id.lb_price);

        grid_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int product_id,product_price,product_sl;
                String product_name,img;

                product_id = array_product.get(i).getProduct_id();
                product_price = array_product.get(i).getProduct_price();
                product_sl = 1;
                product_name = array_product.get(i).getProduct_name();
                img = array_product.get(i).getImg();
                Order_Detail order_detail = new Order_Detail(product_id,product_price,product_sl,product_name,img);

                for (Order_Detail item:array_detail) {
                    if (item.getProduct_id() == product_id)
                        return;
                }

                array_detail.add(order_detail);
                detail_adapter.notifyDataSetChanged();
                Update_Total();
            }
        });


        list_detail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                array_detail.remove(i);
                detail_adapter.notifyDataSetChanged();
                Update_Total();
                return false;
            }
        });

        list_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Update_Total();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.bt_save){
            try {


                if (detail_adapter.getCount() <= 0) {
                    Check_Connect.Toast_Short(getApplicationContext(), "Chưa có chi tiết đơn hàng !");
                } else {
                    Show_Save_Dialog();
                }
            }catch (Exception ec){

            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void Show_Save_Dialog(){
        final Dialog dialog =  new Dialog(Order_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sale_order_add);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final Spinner list_table = (Spinner)dialog.findViewById(R.id.cb_table);
        txt_khachhang = dialog.findViewById(R.id.txt_customer);

        array_table = new ArrayList<String>();
        table_adapter = new Combobox_Adapter(getApplicationContext(),array_table,R.layout.simple_line);
        list_table.setAdapter(table_adapter);
        get_Table();

        list_table.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                table = array_table.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String khachhang = txt_khachhang.getText().toString();
                if (khachhang.length()<=0){
                    Check_Connect.Toast_Short(getApplicationContext(),"Tên khách hàng không thể rỗng !");
                }
                else
                {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.order_add, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if (response.toString().equals("1")) {
                                Check_Connect.Toast_Short(getApplicationContext(), "Thêm thành công !");
                                array_detail.clear();
                                detail_adapter.notifyDataSetChanged();
                                lb_price.setText("Tổng cộng: 0 VND");
                            }

                            else
                                Check_Connect.Toast_Short(getApplicationContext(),"Thêm không thành công !");
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String content="";
                            int count =0;
                            for (Order_Detail item:array_detail) {
                                if (count != 0)
                                    content+="\n";
                                content +=item.getProduct_name()+". . . . ."+item.getProduct_price()+" VND *"+item.getProduct_sl();
                                count++;

                            }
                            
                            
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("khachhang",khachhang);
                            hashMap.put("table",table);
                            hashMap.put("total",lb_price.getText().toString());
                            hashMap.put("content",content);
                            return hashMap;
                        }
                    };

                    requestQueue.add(stringRequest);
                    dialog.cancel();
                }
            }


        });




        dialog.show();
    }

    public void getData_Product(){
        array_product.clear();
        if (Check_Connect.haveNetworkConnection(getApplicationContext())){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.product_get, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if (response!=null){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int product_id = jsonObject.getInt("product_id");
                                String product_name = jsonObject.getString("product_name");
                                int product_price = jsonObject.getInt("product_price");
                                String product_type = jsonObject.getString("product_type");
                                String product_img = jsonObject.getString("product_img");
                                array_product.add(new Product(product_id,product_price,product_name,product_type,product_img));
                            }
                            product_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String, String>();
                    hashMap.put("name","Tất cả");
                    return hashMap;
                }
            };

            requestQueue.add(stringRequest);
        }else {
            Check_Connect.Toast_Short(getApplicationContext(),"Kết nói không thành công !");
        }

    }

    public void Update_Total(){
        int total=0;
        for (Order_Detail item:array_detail) {
            total+=(item.getProduct_sl()*item.getProduct_price());
        }
        lb_price.setText("Tổng cộng: "+ total+" VND");
    }

    public void get_Table(){
        array_table.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.table_data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i = 0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            array_table.add(name);

                            table_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Check_Connect.Toast_Short(getApplicationContext(),"Kết nói không thành công !");
                finish();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }






}
