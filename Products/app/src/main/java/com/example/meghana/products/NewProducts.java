package com.example.meghana.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by meghana on 9/8/16.
 */
public class NewProducts extends AppCompatActivity {

    EditText name, cost;
    DatabaseHelper db;
    Button btaddproduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproducts);

       btaddproduct = (Button) findViewById(R.id.fabproductadd);
        name = (EditText) findViewById(R.id.pname);
        cost = (EditText) findViewById(R.id.pcost);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    public void addproducts(View view) {

        String a = name.getText().toString();
        String b = cost.getText().toString();


        if (a.trim().length() == 0) {

            name.setError("please enter product name");
            return;
        }
        if (b.trim().length() == 0) {

            cost.setError("please enter the cost");
            return;

        }

        if (a.length() != 0 && b.length() != 0) {

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();


            Log.d("products", "" + a + b);

            db.saveDataToProductTable(a, b);
            finish();

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

        if (id == android.R.id.home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}