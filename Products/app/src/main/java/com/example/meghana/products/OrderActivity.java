package com.example.meghana.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.example.meghana.products.adapter.CustomAdapter;
import com.example.meghana.products.adapter.ProductAdapter;
import com.example.meghana.products.adapter.SwipeRecyclerViewAdapter;
import com.example.meghana.products.model.Customer;
import com.example.meghana.products.model.Products;

import java.util.ArrayList;


/**
 * Created by meghana on 9/8/16.
 */
public class OrderActivity extends AppCompatActivity {


    Spinner spinnercustomer;
    Spinner spinnerproducts;
    RecyclerView mRecyclerView;
    ObjectForUse Data;

    ArrayList<Customer> cnames;
    ArrayList<Products> pnames;


    Button order;
    EditText quantity;
    TextView totalcost, ptotal;
    EditText find;
    Button btnGo;
    Bundle b;
    DatabaseHelper db;
    String selCustomer, selProduct, productname, customername;
    String total;
    TextView tvempty;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);


        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        b = getIntent().getBundleExtra("bundle");

        Data = (ObjectForUse) getIntent().getSerializableExtra("users");


        cnames = db.getCustomernames();
        pnames = db.getProductnames();
        order = (Button) findViewById(R.id.order);
        quantity = (EditText) findViewById(R.id.quantity);
        totalcost = (TextView) findViewById(R.id.v_total_val);
        ptotal = (TextView) findViewById(R.id.totaltext);
        find = (EditText) findViewById(R.id.EditTextForSearch);
        btnGo = (Button) findViewById(R.id.btnForSearch);
        tvempty = (TextView)findViewById(R.id.tvNoOrders);



        ArrayList<ObjectForUse> orders = db.getOrderdata();


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, R.layout.swipelayout, orders);


        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

        /* Scroll Listeners */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        spinnercustomer = (Spinner) findViewById(R.id.cspinner);
        spinnerproducts = (Spinner) findViewById(R.id.pspinner);


        CustomAdapter customadapter = new CustomAdapter(this, R.layout.support_simple_spinner_dropdown_item, cnames);
        spinnercustomer.setAdapter(customadapter);

        spinnercustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get select item
                int sid = spinnercustomer.getSelectedItemPosition();
                Customer customer = (Customer) parent.getItemAtPosition(position);
                Log.d("customer : ", String.valueOf(sid) + customer.getName());
                selCustomer = customer.getId();
                customername = customer.getName();


                //   Log.d("customer : ", String.valueOf(sid)+parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


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
                productname = products.getPname();
                total = products.getPcost();
                Log.d("onItemSelected: ", total);
//                name.setText(products.getPname());
//                item.setText(products.getPcost());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        if (getSupportActionBar() != null) {


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }




    public void findApp(View view) {

        String name = find.getText().toString();
        String d = getProductid(name);
        Log.d("pname ", name);
        if (name.trim().length() == 0) {
            find.setError("please enter some product");
            return;
        }
        if (d == null) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            totalcost.setVisibility(View.INVISIBLE);
            ptotal.setVisibility(View.INVISIBLE);
            tvempty.setVisibility(View.VISIBLE);

            return;

        }
        if (name.length() != 0) {
            ArrayList<ObjectForUse> list1 = db.searchApp(d);
            Log.d("findAppD: ", d);
            SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, R.layout.swipelayout, list1);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setVisibility(View.VISIBLE);
            totalcost.setVisibility(View.VISIBLE);
            ptotal.setVisibility(View.VISIBLE);
            tvempty.setVisibility(View.INVISIBLE);

           int totalvalue = 0;
            Log.d( "totalvalue: ",d);
            for (int i=0;i<list1.size();i++){
                Log.d("findApp: ", String.valueOf(list1.size()));
                ObjectForUse obj =list1.get(i);
                totalvalue= totalvalue+Integer.parseInt(obj.o_amount);
            }
            totalcost.setText(String.valueOf(totalvalue));

        }
        find.setText("");
    }



    public String getProductid(String name) {
        String id = db.getProductid(name);
        return id;

    }





    @Override
    protected void onResume() {
        super.onResume();



        final ArrayList<ObjectForUse> orders = db.getOrderdata();


        SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, R.layout.swipelayout,orders);


        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);


        /* Scroll Listeners */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        String totalvalue = db.getTotal();
        totalcost.setText(totalvalue);
        Log.d( "totalcost ",totalvalue);
        tvempty.setVisibility(View.INVISIBLE);

    }



    public void orderproduct(View view){

        if(quantity.getText().toString().length() > 0) {//Ok
            int a = Integer.parseInt(quantity.getText().toString());
            String b = selCustomer;
            String c= selProduct;
            int d = Integer.parseInt(total);
            String totalamount=String.valueOf(d*a);
            String e = productname;
            String f = customername;

            if(a > 0) {
                db.addOrders(a,b,c,totalamount,e,f);
                Toast.makeText(this,"Your order will proceed and Total cost is" +totalamount, Toast.LENGTH_SHORT).show();
                ArrayList<ObjectForUse> orders = db.getOrderdata();

                SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, R.layout.swipelayout,orders);


                // Setting Mode to Single to reveal bottom View for one item in List
                // Setting Mode to Mutliple to reveal bottom Views for multile items in List
                ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

                mRecyclerView.setAdapter(mAdapter);

        /* Scroll Listeners */
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.e("RecyclerView", "onScrollStateChanged");
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });


                String totalvalue = db.getTotal();
                totalcost.setText(totalvalue);
                Log.d( "totalcost ",totalvalue);
            }
        }else {//Error - null value
            quantity.setError("please enter quantity");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id)

        {
            case R.id.action_settings:
                return true;

            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}
