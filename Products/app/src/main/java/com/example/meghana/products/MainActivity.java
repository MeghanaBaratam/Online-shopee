package com.example.meghana.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button customer, products, orders;
    ArrayList<ObjectForUse> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customer = (Button) findViewById(R.id.btcustomers);
        products = (Button) findViewById(R.id.btproducts);
        orders = (Button) findViewById(R.id.btorders);



    }


    public void customers(View view) {

        Intent intent = new Intent(this, CustomerActivity.class);
        Log.d("click", "CustomerList: ");
        Bundle b = new Bundle();
        b.putSerializable("Users", list);
        intent.putExtra("bundle", b);
        startActivity(intent);
    }

    public void products(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        Log.d("click", "ProductList: ");
        Bundle b = new Bundle();
        b.putSerializable("Users", list);
        intent.putExtra("bundle", b);
        startActivity(intent);
    }


    public void orders(View view) {

        Intent intent = new Intent(this, OrderActivity.class);
        Log.d("click", "ProductList: ");
        Bundle b = new Bundle();
        b.putSerializable("Users", list);
        intent.putExtra("bundle", b);
        startActivity(intent);
    }




}
