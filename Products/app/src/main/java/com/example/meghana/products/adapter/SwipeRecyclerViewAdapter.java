package com.example.meghana.products.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.meghana.products.DatabaseHelper;
import com.example.meghana.products.ObjectForUse;
import com.example.meghana.products.OrderActivity;
import com.example.meghana.products.PopUp;
import com.example.meghana.products.R;

import java.util.ArrayList;

/**
 * Created by meghana on 22/8/16.
 */
public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {
    Context context;
    ArrayList<ObjectForUse> data;
    DatabaseHelper db;
    int id;

    public SwipeRecyclerViewAdapter(Context context, int resource, ArrayList<ObjectForUse> data) {
        this.context = context;
        this.data = data;
        this.id = resource;
        db = new DatabaseHelper(context);
    }

    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvName;
        TextView tvcost;
        TextView tvDelete;
        TextView tvEdit;



        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvName = (TextView) itemView.findViewById(R.id.v_fromvalue);
            tvcost = (TextView) itemView.findViewById(R.id.v_postvalue);
            tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
            tvEdit = (TextView) itemView.findViewById(R.id.tvEdit);



        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipelayout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        final ObjectForUse s = data.get(position);


        viewHolder.tvName.setText(s.o_pname);


        viewHolder.tvcost.setText(s.o_amount);


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);


        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });


        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, " onClick : " +viewHolder.tvName.getText().toString()+viewHolder.tvcost.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.cancelOrder(s.o_id);
                Toast.makeText(context, "Your order has been cancelled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("users", s);
                context.startActivity(intent);
                Toast.makeText(view.getContext(), "Deleted "  +  viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PopUp.class);
                intent.putExtra("users", s);
                context.startActivity(intent);
                Toast.makeText(view.getContext(), "Clicked on Edit " + viewHolder.tvName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
