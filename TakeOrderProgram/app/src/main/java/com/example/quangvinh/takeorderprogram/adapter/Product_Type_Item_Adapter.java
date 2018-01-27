package com.example.quangvinh.takeorderprogram.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quangvinh.takeorderprogram.R;
import com.example.quangvinh.takeorderprogram.model.Product_Type;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by QuangVinh on 1/25/2018.
 */

public class Product_Type_Item_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product_Type> array_product_type;
    private int layout;

    public Product_Type_Item_Adapter(Context context, ArrayList<Product_Type> array_product_type, int layout) {
        this.context = context;
        this.array_product_type = array_product_type;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return array_product_type.size();
    }

    @Override
    public Object getItem(int i) {
        return array_product_type.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        ImageView img = (ImageView)view.findViewById(R.id.img_grid_item);
        TextView lb_name = (TextView)view.findViewById(R.id.lb_item_grid);

        lb_name.setText(array_product_type.get(i).getName());

        Product_Type product_type = (Product_Type) getItem(i);
                Picasso.with(context).load(array_product_type.get(i).getImg()).placeholder(R.drawable.no_image)
                .error(R.drawable.kind)
                .into(img);

        lb_name.setSelected(true);

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
