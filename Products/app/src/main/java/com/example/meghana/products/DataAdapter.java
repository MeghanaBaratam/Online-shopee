package com.example.meghana.products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meghana on 9/8/16.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.SimpleViewHolder> {

    Context context;
    List<ObjectForUse> data;
    int id;


    public DataAdapter(Context context, int resource, ArrayList<ObjectForUse> objects) {

        this.context = context;
        data = objects;
        id = resource;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView t1, t2;

        public SimpleViewHolder(View view) {
            super(view);
            t1 = (TextView) view.findViewById(R.id.v_o_pname);
            t2 = (TextView) view.findViewById(R.id.v_o_pcost);

        }
    }

    @Override
    public DataAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plist_item, parent, false);


        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

        final ObjectForUse s = data.get(position);


        holder.t1.setText(s.o_pname);
        holder.t2.setText(s.o_cost);

    }


    @Override
    public int getItemCount() {

            return data.size();

    }
}