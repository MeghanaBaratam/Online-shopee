package com.example.meghana.products.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meghana.products.R;
import com.example.meghana.products.model.Customer;

import java.util.List;

/**
 * Created by meghana on 11/8/16.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Customer> data;
    int id;

    public CustomAdapter(Context context,int resource, List<Customer> data) {
        this.context = context;
        this.data = data;
        this.id=resource;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        convertView= inflater.inflate(R.layout.layout_spinner, parent, false);


        Customer item = data.get(position);

        TextView customername = (TextView) convertView.findViewById(R.id.countryName);

            customername.setText(item.getName());


        return convertView;
    }



}
