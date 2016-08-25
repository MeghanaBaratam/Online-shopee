package com.example.meghana.products.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.meghana.products.ObjectForUse;

import java.util.List;

/**
 * Created by meghana on 23/8/16.
 */
public class SingleAdapter extends ArrayAdapter<ObjectForUse> {

    Context context;
    List<ObjectForUse> data;
    int id;

    public SingleAdapter(Context context, int resource, List<ObjectForUse> objects) {
        super(context, resource, objects);
        this.context = context;
        data = objects;
        id = resource;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(id, parent, false);

//        TextView t1 = (TextView) convertView.findViewById(R.id.grid_text);


        final ObjectForUse o = data.get(position);
        Log.d("data", "getView: ");


//        t1.setText(o.o_pname);

        return convertView;

    }
}

