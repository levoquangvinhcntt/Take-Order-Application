package com.example.quangvinh.takeorderprogram.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quangvinh.takeorderprogram.Order_Activity;
import com.example.quangvinh.takeorderprogram.R;
import com.example.quangvinh.takeorderprogram.model.Order_Detail;
import com.example.quangvinh.takeorderprogram.model.Product;
import com.example.quangvinh.takeorderprogram.util.Check_Connect;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by QuangVinh on 1/27/2018.
 */

public class Order_Detail_Adapter extends BaseAdapter {
    private int layout;
    private Context context;
    private ArrayList<Order_Detail> array_detail;
    Order_Activity order_activity;

    public Order_Detail_Adapter(Order_Activity order_activity,Context context, ArrayList<Order_Detail> array_detail, int layout) {
        this.context = context;
        this.order_activity = order_activity;
        this.array_detail = array_detail;
        this.layout = layout;
    }



    @Override
    public int getCount() {
        return array_detail.size();
    }

    @Override
    public Object getItem(int i) {
        return array_detail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txt_name = (TextView)view.findViewById(R.id.lb_tensp);
        TextView txt_price = (TextView)view.findViewById(R.id.lb_giasp);
        final TextView txt_sl = (TextView)view.findViewById(R.id.lb_product_sl);
        ImageView img = (ImageView)view.findViewById(R.id.sp_img);
        final Button bt_up = (Button)view.findViewById(R.id.bt_up);
        final Button bt_down = (Button)view.findViewById(R.id.bt_down);

        txt_name.setText(array_detail.get(i).getProduct_name());
        txt_price.setText(array_detail.get(i).getProduct_price()+" VND");
        txt_sl.setText(String.valueOf(array_detail.get(i).getProduct_sl()));
        Picasso.with(context).load(array_detail.get(i).getImg()).placeholder(R.drawable.maxresdefault)
                .error(R.drawable.no_image)
                .into(img);

        txt_name.setSelected(true);
        txt_price.setSelected(true);

        final int posisiton = i;

        bt_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (array_detail.get(posisiton).getProduct_sl() >= 10){
                    Check_Connect.Toast_Short(context,"Sl đã tối đa !");
                    bt_up.setEnabled(false);
                    bt_down.setEnabled(true);
                }
                else {
                    txt_sl.setText(String.valueOf(array_detail.get(posisiton).getProduct_sl()+1));
                    array_detail.get(posisiton).setProduct_sl(Integer.parseInt(txt_sl.getText().toString()));
                    order_activity.Update_Total();
                    bt_down.setEnabled(true);
                }
            }
        });

        bt_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (array_detail.get(posisiton).getProduct_sl() <= 1){
                    Check_Connect.Toast_Short(context,"Sl đã tối thiểu !");
                    bt_down.setEnabled(false);
                    bt_up.setEnabled(true);
                }
                else {
                    txt_sl.setText(String.valueOf(array_detail.get(posisiton).getProduct_sl()-1));
                    array_detail.get(posisiton).setProduct_sl(Integer.parseInt(txt_sl.getText().toString()));
                    order_activity.Update_Total();
                    bt_up.setEnabled(true);
                }
            }
        });









        return view;
    }





    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
