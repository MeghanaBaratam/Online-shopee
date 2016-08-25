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
public class ProductActivity extends AppCompatActivity {


    DatabaseHelper db;
    RecyclerView mrecyclerView;
    ArrayList<ObjectForUse> productData;
    Bundle b;
    TextView tvProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();
        b = getIntent().getBundleExtra("bundle");
//
//        productData = (ArrayList<ObjectForUse>) b.getSerializable("Users");

        tvProduct = (TextView)findViewById(R.id.tvNoProducts);
        mrecyclerView = (RecyclerView) findViewById(R.id.precycler);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (getSupportActionBar() != null) {


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this,NewProducts.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        productData = db.getDataFromProductTable();
        DataAdapter  dataAdapter = new DataAdapter(this, R.layout.plist_item, productData);
        mrecyclerView.setAdapter(dataAdapter);

        if (productData.size()>0){
            tvProduct.setVisibility(View.INVISIBLE);
        }else {
            tvProduct.setVisibility(View.VISIBLE);
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
