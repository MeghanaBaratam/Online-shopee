package com.example.meghana.products;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.meghana.products.model.Customer;
import com.example.meghana.products.model.Products;

import java.util.ArrayList;

/**
 * Created by meghana on 9/8/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase sdb;

    public static final String DATABASE_NAME = "RegDb.db";
    public static final String TABLE_NAME = "Customers_table";
    public static final String TABLE_NAME1 = "Products_table";
    public static final String TABLE_NAME2 = "Orders_table";

    public static final String COL_1 = "Customer_id";
    public static final String COL_2 = "Customer_Name";
    public static final String COL_3 = "Product_id";
    public static final String COL_4 = "Product_Name";
    public static final String COL_5 = "Cost";
    public static final String COL_6 = "Order_id";
    public static final String COL_7 = "Amount";
    public static final String COL_8 = "Quantity";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("create table", "table1");
        db.execSQL("create table " + TABLE_NAME + "(Customer_id INTEGER PRIMARY KEY AUTOINCREMENT,Customer_Name VARCHAR)");

        Log.d("create table", "table2");
        db.execSQL("create table " + TABLE_NAME1 + "(Product_id INTEGER PRIMARY KEY AUTOINCREMENT,Product_Name VARCHAR,Cost VARCHAR)");

        Log.d("create table", "table3");
        db.execSQL("create table " + TABLE_NAME2 + "(Order_id INTEGER PRIMARY KEY AUTOINCREMENT,Customer_id INTEGER,Product_id INTEGER,Amount VARCHAR," +
                "Quantity Integer,Product_Name VARCHAR,Customer_Name VARCHAR, FOREIGN KEY(Customer_id)REFERENCES Customers_table(Customer_id),FOREIGN KEY(Product_id)REFERENCES Products_table(Product_id)" +
                " FOREIGN KEY(Product_Name)REFERENCES Products_table(Product_Name) )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME2);

        onCreate(db);

    }

    public void openDb() {
        sdb = this.getWritableDatabase();
    }

    public void closeDb() {
        sdb.close();
    }


    public void saveDataToCustomerTable(String name) {

        openDb();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);


        long sdataRes = sdb.insert(TABLE_NAME, null, contentValues);
        Log.d("sdataRes", "" + sdataRes);

        closeDb();

    }

    public void saveDataToProductTable(String name, String cost) {

        openDb();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, name);
        contentValues.put(COL_5, cost);

        long pdataRes = sdb.insert(TABLE_NAME1, null, contentValues);
        Log.d("pdataRes", "" + pdataRes);

        closeDb();
    }

    public ArrayList<ObjectForUse> getDataFromCustomerTable() {
        Log.d("enter", "getDataFromCustomerTable: ");

        openDb();
        Cursor cur = sdb.query(true, TABLE_NAME, null, null, null, null, null, null, null);
        ArrayList<ObjectForUse> listData = new ArrayList<>();
        if (cur.getCount() > 0) {
            for (; cur.moveToNext(); ) {
                ObjectForUse obj = new ObjectForUse();
                obj.o_cname = cur.getString(cur.getColumnIndex(COL_2));
                listData.add(obj);

            }

            Log.d("list size", "" + listData.size());
        }
        cur.close();
        closeDb();
        return listData;

    }


    public ArrayList<ObjectForUse> getDataFromProductTable() {
        Log.d("enter", "getDataFromProductTable");

        openDb();

//        Cursor cur =  sdb.query(true, TABLE_NAME1+" ot", new String[]{COL_4, COL_5,
//                "(select product(quantity) from Orders_table where product_id=ot.product_id) cost"  }, null, null, null, null, null, null );
        Cursor cur = sdb.query(true, TABLE_NAME1, null, null, null, null, null, null, null);
        ArrayList<ObjectForUse> listData = new ArrayList<>();
        if (cur.getCount() > 0) {
            for (; cur.moveToNext(); ) {
                ObjectForUse obj = new ObjectForUse();
                obj.o_pname = cur.getString(cur.getColumnIndex(COL_4));
                obj.o_cost = cur.getString(cur.getColumnIndex(COL_5));
                listData.add(obj);

            }

            Log.d("list size", "" + listData.size());
        }
        cur.close();
        closeDb();
        return listData;

    }


    public ArrayList<Customer> getCustomernames() {
        Log.d("enter", "getcustomername");

        openDb();
        Cursor cur = sdb.query(true, TABLE_NAME, new String[]{COL_2, COL_1}, null, null, null, null, null, null);
        ArrayList<Customer> list = new ArrayList<>();
        if (cur.getCount() > 0) {
            for (; cur.moveToNext(); ) {
                /*String temp = cur.getString(cur.getColumnIndex(COL_2));
                Log.d("getCustomernames: ", String.valueOf(cur.getColumnIndex(COL_2) + cur.getColumnIndex(COL_1)));
                list.add(temp);*/

                Customer customer = new Customer(cur.getString(cur.getColumnIndex(COL_1)), cur.getString(cur.getColumnIndex(COL_2)));
                list.add(customer);
            }
        }
        Log.d("list", "" + list.size());
        cur.close();
        closeDb();
        return list;
    }


    public ArrayList<Products> getProductnames() {
        Log.d("enter", "getcustomername");

        openDb();
        Cursor cur = sdb.query(true, TABLE_NAME1, new String[]{COL_4, COL_3, COL_5}, null, null, null, null, null, null);
        ArrayList<Products> list = new ArrayList<>();
        if (cur.getCount() > 0) {
            for (; cur.moveToNext(); ) {
//                String temp = cur.getString(cur.getColumnIndex(COL_4));
//                list.add(temp);

                Products products = new Products(cur.getString(cur.getColumnIndex(COL_3)), cur.getString(cur.getColumnIndex(COL_4)), cur.getString(cur.getColumnIndex(COL_5)));
                list.add(products);

            }
        }
        Log.d("list", "" + list.size());
        cur.close();
        closeDb();
        return list;
    }


    public void addOrders(int a, String b, String c, String d,String e,String f) {

        Log.d("enter", "addOrders: ");


        openDb();


        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_8, a);
        contentvalues.put(COL_1, b);
        contentvalues.put(COL_3, c);
        contentvalues.put(COL_7, d);
        contentvalues.put(COL_4, e);
        contentvalues.put(COL_2,f);

        long pdataRes = sdb.insert(TABLE_NAME2, null, contentvalues);
        Log.d("pdataRes", "" + pdataRes);

        closeDb();


    }

    public ArrayList<ObjectForUse> getOrderdata() {
        Log.d("enter", "getDataFromorderdata");

        openDb();


        Cursor cur = sdb.query(true, TABLE_NAME2 + " pt", new String[]{"(select product_name from products_table where product_id=pt.product_id) product_name",

                "(select cost from products_table where product_id=pt.product_id) cost",
                COL_8, COL_7, COL_3, COL_6}, null, null, null, null, null, null);

//        Cursor cur = sdb.query(true, TABLE_NAME2, null, null, null, null, null, null, null);
        ArrayList<ObjectForUse> listData = new ArrayList<>();
        if (cur.getCount() > 0) {
            for (; cur.moveToNext(); ) {
                ObjectForUse obj = new ObjectForUse();
                obj.o_pname = cur.getString(0);
                obj.o_pid = cur.getString(cur.getColumnIndex(COL_3));
                obj.o_amount = cur.getString(cur.getColumnIndex(COL_7));
                obj.o_quantity = cur.getString(cur.getColumnIndex(COL_8));
                obj.o_id = cur.getString(cur.getColumnIndex(COL_6));
                Log.d("ORDER", obj.o_id);
                obj.o_cost = cur.getString(1);
                Log.d("ORDER", obj.o_cost);
                listData.add(obj);

            }

            Log.d("list size", "" + listData.size());
        }
        cur.close();
        closeDb();
        return listData;


    }

    public boolean EditQuantity(String o_id, String quantity, String amount, String c) {
        openDb();
        ContentValues content = new ContentValues();
        content.put(COL_8, quantity);
        content.put(COL_7, amount);
        content.put(COL_3, c);
        long upd = sdb.update(TABLE_NAME2, content, "Order_id=?", new String[]{o_id});
        Log.d("upd:", upd + "");
        closeDb();
        if (upd == -1) {
            return false;
        }
        return true;
    }


    public boolean cancelOrder(String o_id) {

        openDb();
        long Rows = sdb.delete(TABLE_NAME2, "Order_id=?", new String[]{o_id});
        Log.d("noOfRows", Rows + "");
        closeDb();
        if (Rows == 0) {
            return false;
        }
        return true;
    }

    public String getTotal() {

        openDb();
        Cursor cursor = sdb.query(TABLE_NAME2, new String[]{"(select sum(amount) from orders_table) amount"},
                null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            String total = cursor.getString(cursor.getColumnIndex("amount"));

            return total;
        } else {
            return String.valueOf(0);
        }


    }

    public ArrayList<ObjectForUse> searchApp(String p_id) {
        Log.d("searchApp: ","pname");
        ArrayList<ObjectForUse> list1 = new ArrayList<>();

       Cursor cur = sdb.query(TABLE_NAME2,new String[]{COL_4,COL_7},"Product_id=?",new String[]{p_id},null,null,null,null);
        if (cur.getCount() > 0) {
            Log.d("list size7", "" + cur.getCount());
            while (cur.moveToNext()){
                ObjectForUse obj = new ObjectForUse();
                obj.o_pname = cur.getString(0);
                Log.d("appname", obj.o_pname);
                obj.o_amount=cur.getString(cur.getColumnIndex(COL_7));




                list1.add(obj);
            }
            Log.d("list size", "" + list1.size());
        }
        cur.close();


        return list1;
    }

    public String getProductid(String product_name){
        String o_pid = null;

        Cursor cur= sdb.rawQuery("SELECT Product_id from " + "Orders_table" + " where " +"Product_Name"+ " like '%"+product_name+"%'" , null);
      //  Cursor cur = sdb.query(TABLE_NAME1,new String[]{COL_3},"Product_Name=?",new String[]{product_name},null,null,null,null);
        if (cur.moveToFirst()){

            o_pid = cur.getString(cur.getColumnIndex(COL_3));

        }
        cur.close();
        return o_pid;
    }



}








