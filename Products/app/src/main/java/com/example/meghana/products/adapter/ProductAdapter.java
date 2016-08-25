package com.example.meghana.products.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meghana.products.R;
import com.example.meghana.products.model.Products;

import java.util.List;

/**
 * Created by meghana on 11/8/16.
 */
public class ProductAdapter extends BaseAdapter {
    Context context;
    List<Products> data;
    int id;

    public ProductAdapter(Context context,int resource,List<Products> data) {
        this.context = context;
        this.id= resource;
        this.data=data;
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


        Products item = data.get(position);

        TextView productname = (TextView) convertView.findViewById(R.id.countryName);

        productname.setText(item.getPname());


        return convertView;
    }
}
