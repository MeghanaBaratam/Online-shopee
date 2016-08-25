package com.example.meghana.products.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meghana.products.DatabaseHelper;
import com.example.meghana.products.ObjectForUse;
import com.example.meghana.products.R;

import java.util.ArrayList;


/**
 * Created by meghana on 22/8/16.
 */
public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.SimpleViewHolder> {

    Context context;
    ArrayList<ObjectForUse> data;


    DatabaseHelper db;
    int id;

    public Order_Adapter(Context context, int resource, ArrayList<ObjectForUse> data) {
        this.context = context;
        this.data = data;
        this.id = resource;
        db = new DatabaseHelper(context);

    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView t1;



        public SimpleViewHolder(View view) {
            super(view);
            t1 = (TextView) view.findViewById(R.id.v_fromvalue);



        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_single, null);




        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {


//        final ObjectForUse s = data.get(position);

        holder.t1.setText((CharSequence) data.get(position));
         holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, (CharSequence) data.get(position), Toast.LENGTH_LONG).show();
                Log.d( "onClickadapetr: ", String.valueOf(position));
            }
        });





    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
