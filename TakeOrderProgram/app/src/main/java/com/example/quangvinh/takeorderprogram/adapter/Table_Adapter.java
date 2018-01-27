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
import com.example.quangvinh.takeorderprogram.model.Product_Type;
import com.example.quangvinh.takeorderprogram.model.Table;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by QuangVinh on 1/26/2018.
 */

public class Table_Adapter extends BaseAdapter{

    private Context context;
    private ArrayList<Table> array_table;
    private int layout;

    public Table_Adapter(Context context, ArrayList<Table> array_table, int layout) {
        this.context = context;
        this.array_table = array_table;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return array_table.size();
    }

    @Override
    public Object getItem(int i) {
        return array_table.get(i);
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
        lb_name.setText(array_table.get(i).getTable_name());
        img.setImageResource(R.drawable.table);
        lb_name.setSelected(true);

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
