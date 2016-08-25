package com.example.meghana.products;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meghana.products.adapter.ProductAdapter;
import com.example.meghana.products.model.Products;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meghana on 16/8/16.
 */
public class PopUp extends AppCompatActivity {

    ObjectForUse data;
    TextView textitem, total;
    EditText etqty;
    DatabaseHelper db;
    ArrayList<Products> pnames;
    Spinner spinnerproducts;
    String selProduct;
    String totalamount;
    int prod_pos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        data = (ObjectForUse) getIntent().getSerializableExtra("users");

        pnames = db.getProductnames();

        etqty = (EditText) findViewById(R.id.EditTextForquantity);
        textitem = (TextView) findViewById(R.id.textorder);
//        total=(TextView)findViewById(R.id.textname);
        spinnerproducts = (Spinner) findViewById(R.id.pspinner);

//        assert total != null;
//        total.setText(data.o_cost);
//        textitem.setText(data.o_pname);
        etqty.setText(data.o_quantity);


        ProductAdapter productadapter = new ProductAdapter(this, R.layout.support_simple_spinner_dropdown_item, pnames);
        spinnerproducts.setAdapter(productadapter);


        spinnerproducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get select item
                int sid = spinnerproducts.getSelectedItemPosition();
                Products products = (Products) parent.getItemAtPosition(position);
                Log.d("product : ", String.valueOf(sid) + products.getPname());
                selProduct = products.getPid();
                totalamount = products.getPcost();
//                total.setText(products.getPname());
                textitem.setText(products.getPcost());


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        // set selected for product spinner
        int i = 0;
        for (Products p : pnames) {
            if (data.o_pname.equalsIgnoreCase(p.getPname())) {
                prod_pos = i;
                break;
            }
            i++;
        }
        spinnerproducts.setSelection(prod_pos);
    }


    public void updateData(View view) {
        if (etqty.getText().toString().length() > 0) {
            int a = Integer.parseInt(etqty.getText().toString());
            int d = Integer.parseInt(totalamount);
            String c = selProduct;
            String totalamount = String.valueOf(d * a);

            if (a > 0) {
                db.EditQuantity(data.o_id, etqty.getText().toString(), totalamount, c);
                setResult(RESULT_OK);
                finish();
                createNotification();
            }
        } else {
            etqty.setError("please enter quantity");
        }

    }


    public void createNotification() {



        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String longText = "your order has been updated " +data.o_id;

        Intent notifyintent = new Intent(this, NotificationReciever.class);
        notifyintent.putExtra("IS_NOTIFICATION", true);
        notifyintent.putExtra("NOTIFICATION_MSG", longText);

        notifyintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        Date now = new Date();
        long uniqueId = now.getTime();//use date to generate an unique id to differentiate the notifications.
//
//        notifyintent.setAction("com.sample.myapp" + uniqueId);


        PendingIntent intent =
                PendingIntent.getActivity(this, 0, notifyintent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("order update notification")
                .setContentText(longText)
                .setSmallIcon(R.drawable.redmi)
                .setStyle(new Notification.BigTextStyle().bigText(longText))
                .setTicker("notification from orders")
                .setContentIntent(intent).build();

        Log.d("notify", "createNotification: ");

        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        /** Set the unique id to let Notification Manager knows this is a another notification instead of same notification.
         *  If you use the same uniqueId for each notification, the Notification Manager will assume that is same notification and would replace the previous notification. **/
        notificationManager.notify(0, noti);
    }

    }






