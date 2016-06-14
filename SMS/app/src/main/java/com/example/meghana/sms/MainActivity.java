package com.example.meghana.sms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText number,message;
    TextView tv1,tv2,tv3;
    private static final int PERMISSION_REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        number = (EditText)findViewById(R.id.etnumber);
        message = (EditText)findViewById(R.id.etmessage);
        tv1 =(TextView)findViewById(R.id.textview1);
        tv2 = (TextView)findViewById(R.id.textview2);
        tv3 = (TextView)findViewById(R.id.title1);

        if (!checkPermission())
        {
            requestPermission();
        }

        Log.d("call request permission", "onCreate: ");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendSms();
            }
        });

        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/Roboto-MediumItalic.ttf");
        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        tv3.setShadowLayer(5,1,1, Color.DKGRAY);


    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED){
            Log.d("permission",Manifest.permission.SEND_SMS);

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){

            Toast.makeText(getApplicationContext(),"Requesting to send the messages",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},PERMISSION_REQUEST_CODE);
            Log.d("requestPermission: ", String.valueOf(PERMISSION_REQUEST_CODE));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("Permission", String.valueOf(PERMISSION_REQUEST_CODE));

                    Toast.makeText(getApplicationContext(),"Permission Granted, Now you can send message.",Toast.LENGTH_LONG).show();

//                    Snackbar.make(view,"Permission Granted, Now you can send message.",Snackbar.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(),"Permission Denied, Now you cannot send message.",Toast.LENGTH_LONG).show();

//                    Snackbar.make(view,"Permission Denied, You cannot send message.",Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }


    protected void sendSms() {
        String no = number.getText().toString();
        String msg = message.getText().toString();
        if (no.length()< 3 || no.length()> 10){
            number.setError("Enter valid number");
        }


        else {


            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(no, null, msg, null, null);
            number.setText("");
            message.setText("");
            Toast.makeText(getApplicationContext(), "Sent Succesfully", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }
}
