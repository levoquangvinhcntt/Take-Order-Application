package com.example.quangvinh.takeorderprogram.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quangvinh.takeorderprogram.R;
import com.example.quangvinh.takeorderprogram.model.Table;

import java.util.ArrayList;

/**
 * Created by QuangVinh on 1/26/2018.
 */

public class Combobox_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> array_type;
    private int layout;

    public Combobox_Adapter(Context context, ArrayList<String> array_type, int layout) {
        this.context = context;
        this.array_type = array_type;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return array_type.size();
    }

    @Override
    public Object getItem(int i) {
        return array_type.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView lb_name = (TextView)view.findViewById(R.id.lb_name);
        lb_name.setText(array_type.get(i));
        lb_name.setSelected(true);
        LinearLayout layout = view.findViewById(R.id.layout_item);
        if (i%2 == 0){
            layout.setBackgroundResource(R.color.grey);
        }

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
