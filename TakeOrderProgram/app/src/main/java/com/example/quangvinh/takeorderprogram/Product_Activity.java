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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quangvinh.takeorderprogram.adapter.Combobox_Adapter;
import com.example.quangvinh.takeorderprogram.adapter.Product_Adapter;
import com.example.quangvinh.takeorderprogram.adapter.Product_Type_Item_Adapter;
import com.example.quangvinh.takeorderprogram.model.Product;
import com.example.quangvinh.takeorderprogram.model.Product_Type;
import com.example.quangvinh.takeorderprogram.util.Check_Connect;
import com.example.quangvinh.takeorderprogram.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product_Activity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ArrayList<String> array_product_type;
    Combobox_Adapter adapter_type;
    Spinner cb_product_type;
    String type;
    ArrayList<Product> array_product;
    Product_Adapter product_adapter;
    GridView grid_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);
        Mapping();
        getData_Type();
    }

    public void Mapping(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        array_product_type = new ArrayList<String>();
        cb_product_type = findViewById(R.id.cb_product_type);
        adapter_type = new Combobox_Adapter(getApplicationContext(),array_product_type,R.layout.simple_line);
        array_product = new ArrayList<Product>();
        grid_product = (GridView)findViewById(R.id.grid_product);
        product_adapter = new Product_Adapter(getApplicationContext(),array_product,R.layout.product_item);
        grid_product.setAdapter(product_adapter);
        cb_product_type.setAdapter(adapter_type);


        cb_product_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = array_product_type.get(i);
                getData_Product();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_menu, menu);
        return true;
    }

    public void getData_Type(){
        array_product_type.clear();
        array_product_type.add("Tất cả");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i = 0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            array_product_type.add(name);

                            adapter_type.notifyDataSetChanged();
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
                        hashMap.put("name",type);
                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);
        }else {
            Check_Connect.Toast_Short(getApplicationContext(),"Kết nói không thành công !");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.add_table){
            ShowDialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }

    String[] type_name = new String[1];

    public  void ShowDialogAdd(){
        array_product_type.remove("Tất cả");
        final Dialog dialog =  new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.product_add);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        Spinner cb_type = (Spinner)dialog.findViewById(R.id.cb_product_type);
        cb_type.setAdapter(adapter_type);


        cb_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_name[0] = array_product_type.get(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText txt_name = dialog.findViewById(R.id.txt_name);
        final EditText txt_price = dialog.findViewById(R.id.txt_product_price);
        final EditText txt_img = dialog.findViewById(R.id.txt_link_img);



        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                array_product_type.add(0,"Tất cả");
                dialog.cancel();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Check_Connect.haveNetworkConnection(getApplicationContext())){
                    final String name = txt_name.getText().toString().trim();
                    final String img = txt_img.getText().toString().trim();
                    final String price = txt_price.getText().toString().trim();
                    if (name.length()==0){
                        Check_Connect.Toast_Short(getApplicationContext(),"Product Name can't empty !");
                    }
                    else if(img.length()== 0){
                        Check_Connect.Toast_Short(getApplicationContext(),"Product Image can't empty !");
                    }
                    else if (price.length() == 0){
                        Check_Connect.Toast_Short(getApplicationContext(),"Product Price can't empty !");

                    }
                    else
                    {
                        try{
                            int gia = Integer.parseInt(price);
                        }
                        catch (Exception ex){
                            Check_Connect.Toast_Short(getApplicationContext(),"Product Price wrong !");
                            return;
                        }
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.product_add, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                if (response.toString().equals("1"))
                                    Check_Connect.Toast_Short(getApplicationContext(),"Thêm thành công !");
                                else
                                    Check_Connect.Toast_Short(getApplicationContext(),"Thêm không thành công !");

                                getData_Product();
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
                                hashMap.put("name",name);
                                hashMap.put("img",img);
                                hashMap.put("type",type_name[0]);
                                hashMap.put("price",price);
                                return hashMap;
                            }
                        };

                        requestQueue.add(stringRequest);
                        dialog.cancel();
                    }


                }else {
                    Check_Connect.Toast_Short(getApplicationContext(),"Kết nói không thành công !");
                    dialog.cancel();
                }
            }
        });




        dialog.show();
    }



}
