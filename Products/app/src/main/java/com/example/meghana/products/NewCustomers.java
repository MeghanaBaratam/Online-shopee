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
public class NewCustomers extends AppCompatActivity {

    EditText ctname;
    DatabaseHelper db;
    Button btcustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcustomers);

        ctname = (EditText)findViewById(R.id.cname);

      btcustomer = (Button)findViewById(R.id.addfabcustomer);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();


        if (getSupportActionBar() != null) {


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }



    public void addcustomers(View view){

        String a = ctname.getText().toString();



        if (a.trim().length() == 0){
            ctname.setError("please enter customer name");
            return;
        }

        if (a.length()!= 0){

            Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
            Log.d("customers", "" +a);

            db.saveDataToCustomerTable(a);
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

        if (id == android.R.id.home){

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }
}
