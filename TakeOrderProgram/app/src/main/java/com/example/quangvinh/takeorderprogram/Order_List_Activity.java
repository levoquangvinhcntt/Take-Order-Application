package com.example.quangvinh.takeorderprogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quangvinh.takeorderprogram.adapter.Combobox_Adapter;
import com.example.quangvinh.takeorderprogram.model.Table;
import com.example.quangvinh.takeorderprogram.util.Check_Connect;
import com.example.quangvinh.takeorderprogram.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

public class Order_List_Activity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    Combobox_Adapter order_adapter;
    ArrayList<String> array_order;
    ListView list_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);
        Mapping();
        getDataTable();
    }


    private void Mapping(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        array_order = new ArrayList<String>();
        order_adapter = new Combobox_Adapter(getApplicationContext(),array_order,R.layout.simple_line_2);
        list_order = (ListView) findViewById(R.id.list_order);
        list_order.setAdapter(order_adapter);
        order_adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.add_table){
            Intent intent = new Intent(Order_List_Activity.this,Order_Activity.class);
            startActivity(intent);
            getDataTable();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDataTable(){
        array_order.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.order_get, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i = 0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String content = jsonObject.getString("content");
                            array_order.add(content);
                            order_adapter.notifyDataSetChanged();
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
    public void onBackPressed() {
        getDataTable();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        getDataTable();
        super.onResume();
    }
}
