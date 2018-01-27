package com.example.quangvinh.takeorderprogram.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quangvinh.takeorderprogram.R;
import com.example.quangvinh.takeorderprogram.model.Product;
import com.example.quangvinh.takeorderprogram.model.Product_Type;
import com.example.quangvinh.takeorderprogram.model.Table;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by QuangVinh on 1/26/2018.
 */

public class Product_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> array_product;
    private int layout;

    public Product_Adapter(Context context, ArrayList<Product> array_product, int layout) {
        this.context = context;
        this.array_product = array_product;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return array_product.size();
    }

    @Override
    public Object getItem(int i) {
        return array_product.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        TextView txt_product_name = (TextView)view.findViewById(R.id.lb_product_name);
        TextView txt_product_type = (TextView)view.findViewById(R.id.lb_product_type);
        TextView txt_product_price = (TextView)view.findViewById(R.id.lb_product_price);
        ImageView img = (ImageView)view.findViewById(R.id.img_grid_item);

        txt_product_name.setText("Tên: "+array_product.get(i).getProduct_name());
        txt_product_price.setText("Giá: "+array_product.get(i).getProduct_price()+"VND");
        txt_product_type.setText("Loại: "+array_product.get(i).getProduct_type());


        Picasso.with(context).load(array_product.get(i).getImg()).placeholder(R.drawable.maxresdefault)
                .error(R.drawable.no_image)
                .into(img);


        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
