package com.example.meghana.products.adapter;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meghana.products.DatabaseHelper;
import com.example.meghana.products.NotificationReciever;
import com.example.meghana.products.ObjectForUse;
import com.example.meghana.products.OrderActivity;
import com.example.meghana.products.PopUp;
import com.example.meghana.products.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meghana on 11/8/16.
 */
public class OrderAdapter extends BaseAdapter {

    Context context;
   ArrayList<ObjectForUse> data;


    DatabaseHelper db;
    int id;

    public OrderAdapter(Context context, int resource, ArrayList<ObjectForUse> data) {
        this.context = context;
        this.data = data;
        this.id = resource;
        db=new DatabaseHelper(context);

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
        convertView= inflater.inflate(id, parent, false);

        TextView t1= (TextView) convertView.findViewById(R.id.v_fromvalue);
        TextView t2= (TextView) convertView.findViewById(R.id.v_postvalue);
        Button button = (Button)convertView.findViewById(R.id.btcancel);
        Button button1 = (Button)convertView.findViewById(R.id.btupdateorder);

        final ObjectForUse s= data.get(position);



        t1.setText(s.o_pname);


        t2.setText(s.o_amount);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.cancelOrder(s.o_id);
                deleteNotification();
                Toast.makeText(context,"Your order has been cancelled",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,OrderActivity.class);
                intent.putExtra("users",s);
                context.startActivity(intent);
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PopUp.class);
                intent.putExtra("users",s);
                context.startActivity(intent);
            }
        });


        return convertView;

    }

    public void deleteNotification() {




        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        String longText = "your order has been canceled";

        Intent notifyintent = new Intent(context, NotificationReciever.class);
        notifyintent.putExtra("IS_NOTIFICATION", true);
        notifyintent.putExtra("NOTIFICATION_MSG", longText);

        notifyintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        Date now = new Date();
        long uniqueId = now.getTime();//use date to generate an unique id to differentiate the notifications.

        notifyintent.setAction("com.sample.myapp" + uniqueId);


        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notifyintent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification noti = new Notification.Builder(context)
                .setContentTitle("order delete notification")
                .setContentText(longText)
                .setSmallIcon(R.drawable.redmi)
                .setStyle(new Notification.BigTextStyle().bigText(longText))
                .setTicker("cancel notification")
                .setContentIntent(intent).build();

        Log.d("notify", "createNotification: ");

        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        /** Set the unique id to let Notification Manager knows this is a another notification instead of same notification.
         *  If you use the same uniqueId for each notification, the Notification Manager will assume that is same notification and would replace the previous notification. **/
        notificationManager.notify((int) uniqueId, noti);
    }
}
