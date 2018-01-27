package com.example.quangvinh.takeorderprogram;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quangvinh.takeorderprogram.adapter.Product_Type_Item_Adapter;
import com.example.quangvinh.takeorderprogram.model.Product_Type;
import com.example.quangvinh.takeorderprogram.util.Check_Connect;
import com.example.quangvinh.takeorderprogram.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product_Type_Activity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    ArrayList<Product_Type> array_product_type;
    Product_Type_Item_Adapter adapter;
    GridView grid_Product_Type;
    int id = 0;
    String name ="";
    String img ="";
    int positio = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__type_);
        Mapping();
        if (Check_Connect.haveNetworkConnection(getApplicationContext())){
            Check_Connect.Toast_Short(getApplicationContext(),"Kết nói thành công !");
            getData();
        }else {
            Check_Connect.Toast_Short(getApplicationContext(),"Kết nói không thành công !");
            finish();
        }



    }

    public void Mapping(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        grid_Product_Type = (GridView)findViewById(R.id.grid_product_type);
        array_product_type = new ArrayList<Product_Type>();
        adapter = new Product_Type_Item_Adapter(getApplicationContext(),array_product_type,R.layout.product_type_item);
        grid_Product_Type.setAdapter(adapter);
        registerForContextMenu(grid_Product_Type);

        grid_Product_Type.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                positio = i;
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_type_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.product_type_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void getData(){
        array_product_type.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i = 0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("type_id");
                            name = jsonObject.getString("name");
                            img = jsonObject.getString("img");
                            array_product_type.add(new Product_Type(id,name,img));

                            adapter.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_type){
            showDialogAdd();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialogAdd(){
        final Dialog dialog =  new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_product_type);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getData();
                dialog.cancel();
            }
        });

        final EditText txt_name = dialog.findViewById(R.id.txt_name);
        final EditText txt_img = dialog.findViewById(R.id.txt_link_img);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Check_Connect.haveNetworkConnection(getApplicationContext())){
                    final String name = txt_name.getText().toString().trim();
                    final String img = txt_img.getText().toString().trim();
                    if (name.length()==0){
                        Check_Connect.Toast_Short(getApplicationContext(),"Product Type Name can't empty !");
                    }
                    else if(img.length()== 0){
                        Check_Connect.Toast_Short(getApplicationContext(),"Product Type Image can't empty !");
                    }
                    else
                    {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.type_add, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                if (response.toString().equals("1"))
                                    Check_Connect.Toast_Short(getApplicationContext(),"Thêm thành công !");
                                else
                                    Check_Connect.Toast_Short(getApplicationContext(),"Thêm không thành công !");

                                getData();
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.bt_edit){
            showDialogEdit();
        }
        else if (i == R.id.bt_delete){
            Delete_Product_Type();
        }
        return super.onContextItemSelected(item);
    }

    public void showDialogEdit(){
        final Dialog dialog =  new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_product_type);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        bt_add.setText("Update");
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getData();
                dialog.cancel();
            }
        });

        final EditText txt_name = dialog.findViewById(R.id.txt_name);
        final EditText txt_img = dialog.findViewById(R.id.txt_link_img);
        txt_name.setText(array_product_type.get(positio).getName());
        txt_img.setText(array_product_type.get(positio).getImg());

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Check_Connect.haveNetworkConnection(getApplicationContext())){
                    final String name = txt_name.getText().toString().trim();
                    final String img = txt_img.getText().toString().trim();
                    final String id = String.valueOf(array_product_type.get(positio).getType_id());
                    if (name.length()==0){
                        Check_Connect.Toast_Short(getApplicationContext(),"Product Type Name can't empty !");
                    }
                    else if(img.length()== 0){
                        Check_Connect.Toast_Short(getApplicationContext(),"Product Type Image can't empty !");
                    }
                    else
                    {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.type_edit, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                if (response.toString().equals("true"))
                                    Check_Connect.Toast_Short(getApplicationContext(),"Update thành công !");
                                else
                                    Check_Connect.Toast_Short(getApplicationContext(),"Update không thành công !");

                                getData();
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
                                hashMap.put("id",id);
                                hashMap.put("name",name);
                                hashMap.put("img",img);
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

    public void Delete_Product_Type(){
        if (Check_Connect.haveNetworkConnection(getApplicationContext())){
            final String id = String.valueOf(array_product_type.get(positio).getType_id());
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.type_delete, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if (response.toString().equals("true"))
                        Check_Connect.Toast_Short(getApplicationContext(),"Delete thành công !");
                    else
                        Check_Connect.Toast_Short(getApplicationContext(),"Delete không thành công !");

                    getData();
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
                    hashMap.put("id",id);
                    return hashMap;
                }
            };

            requestQueue.add(stringRequest);
        }
        else {
            Check_Connect.Toast_Short(getApplicationContext(),"Kết nói không thành công !");
        }
    }




}



