package com.example.meghana.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by meghana on 9/8/16.
 */
public class CustomerActivity  extends AppCompatActivity{


    DatabaseHelper db;
    RecyclerView mrecyclerView;
    ArrayList<ObjectForUse> customerData;
    Bundle b;
    TextView textcustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customers);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        b = getIntent().getBundleExtra("bundle");

//        customerData = (ArrayList<ObjectForUse>) b.getSerializable("Users");


        textcustomer = (TextView)findViewById(R.id.tvNoCustomers);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this,NewCustomers.class);
                startActivity(intent);
            }
        });

        if (getSupportActionBar() != null) {


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }





        mrecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SimpleAdapter  simpleAdapter = new SimpleAdapter(this, R.layout.listitem, customerData);
        mrecyclerView.setAdapter(simpleAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        customerData = db.getDataFromCustomerTable();
        SimpleAdapter  simpleAdapter = new SimpleAdapter(this, R.layout.listitem, customerData);
        mrecyclerView.setAdapter(simpleAdapter);

        if (customerData.size()>0){
            textcustomer.setVisibility(View.INVISIBLE);
        }else {
            textcustomer.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home){

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }






}
