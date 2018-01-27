package com.example.quangvinh.takeorderprogram;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import com.example.quangvinh.takeorderprogram.adapter.Product_Type_Item_Adapter;
import com.example.quangvinh.takeorderprogram.adapter.Table_Adapter;
import com.example.quangvinh.takeorderprogram.model.Product_Type;
import com.example.quangvinh.takeorderprogram.model.Table;
import com.example.quangvinh.takeorderprogram.util.Check_Connect;
import com.example.quangvinh.takeorderprogram.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table_Activity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ArrayList<Table> array_table;
    Table_Adapter adapter;
    GridView grid_table;
    int id = 0;
    String name ="";
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_);
        Mapping();
        if (Check_Connect.haveNetworkConnection(getApplicationContext())){
            Check_Connect.Toast_Short(getApplicationContext(),"Kết nói thành công !");
            getDataTable();
        }else {
            Check_Connect.Toast_Short(getApplicationContext(),"Kết nói không thành công !");
            finish();
        }
    }

    public void Mapping(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        grid_table = (GridView)findViewById(R.id.grid_tables);
        array_table = new ArrayList<Table>();
        adapter = new Table_Adapter(getApplicationContext(),array_table,R.layout.table_item);
        grid_table.setAdapter(adapter);
        registerForContextMenu(grid_table);
        grid_table.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.product_type_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void getDataTable(){
        array_table.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.table_data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i = 0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("table_id");
                            name = jsonObject.getString("name");
                            array_table.add(new Table(id,name));
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
        if (id == R.id.add_table){
            showDialogAdd();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialogAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_table);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final TextView txt_name = (TextView)dialog.findViewById(R.id.txt_name);
        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getDataTable();
                dialog.cancel();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Check_Connect.haveNetworkConnection(getApplicationContext())) {
                    final String name = txt_name.getText().toString().trim();
                    if (name.length() == 0) {
                        Check_Connect.Toast_Short(getApplicationContext(), "Table Name can't empty !");
                    }
                   else {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.table_add, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                if (response.toString().equals("1"))
                                    Check_Connect.Toast_Short(getApplicationContext(), "Thêm thành công !");
                                else
                                    Check_Connect.Toast_Short(getApplicationContext(), "Thêm không thành công !");

                                getDataTable();
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("name", name);
                                return hashMap;
                            }
                        };

                        requestQueue.add(stringRequest);
                        dialog.cancel();
                    }


                } else {
                    Check_Connect.Toast_Short(getApplicationContext(), "Kết nói không thành công !");
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

    private void showDialogEdit() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_table);
        Button bt_add = dialog.findViewById(R.id.bt_add);
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        TextView lb_name = dialog.findViewById(R.id.lb_ten);
        lb_name.setText("Update Table");
        final EditText txt_name = dialog.findViewById(R.id.txt_name);
        txt_name.setText(array_table.get(position).getTable_name());
        bt_add.setText("Update");



        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getDataTable();
                dialog.cancel();
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Check_Connect.haveNetworkConnection(getApplicationContext())) {
                    final String name = txt_name.getText().toString().trim();
                    final String id = String.valueOf(array_table.get(position).getTable_id());
                    if (name.length() == 0) {
                        Check_Connect.Toast_Short(getApplicationContext(), "Table Name can't empty !");
                    }
                    else {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.table_edit, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                if (response.toString().equals("true"))
                                    Check_Connect.Toast_Short(getApplicationContext(), "Update thành công !");
                                else
                                    Check_Connect.Toast_Short(getApplicationContext(), "Update không thành công !");

                                getDataTable();
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("id",id);
                                hashMap.put("name", name);
                                return hashMap;
                            }
                        };

                        requestQueue.add(stringRequest);
                        dialog.cancel();
                    }


                } else {
                    Check_Connect.Toast_Short(getApplicationContext(), "Kết nói không thành công !");
                    dialog.cancel();
                }
            }
        });



        dialog.show();
    }

    private void Delete_Product_Type() {
        if (Check_Connect.haveNetworkConnection(getApplicationContext())){
            final String id = String.valueOf(array_table.get(position).getTable_id());
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.table_delete, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if (response.toString().equals("true"))
                        Check_Connect.Toast_Short(getApplicationContext(),"Delete thành công !");
                    else
                        Check_Connect.Toast_Short(getApplicationContext(),"Delete không thành công !");

                    getDataTable();
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
