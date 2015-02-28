package com.chaski.optimizedsms.application;
/**
 *
 * MainActivity.java
 * Created by sam and mike on 1/12/15.
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) Chaski Telecommunications, Inc.
 *
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ws4d.coap.messages.CoapRequestCode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends Activity implements View.OnClickListener {
public static final String MY_PREFS_NAME = "optimizedsmsprefs";

boolean fahrenheit = false;
boolean celsius = false;
boolean disabled = false;
long smsincount = 0;
long smsoutcount = 0;
public static final String tag="Activity";

BasicCoapClient bcc;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setThreshold:
                storePhone();
                storeThreshold();
                runPut();
                break;
            case R.id.storeOptions:
                storePhone();
                storeThreshold();
                break;
            case R.id.runQuery:
                storePhone();
                runQuery();
                break;

            case R.id.thresholdCelsius:
                celsius = true;
                fahrenheit=false;
                disabled=false;
                break;

            case R.id.thresholdFahrenheit:
                celsius = false;
                fahrenheit=true;
                disabled=false;
                break;

            case R.id.disableAutoThreshold:
                celsius = false;
                fahrenheit=false;
                disabled=true;
                break;

            case R.id.button3:
                statsDialog();
                break;

        }
    }

    public void storeThreshold() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        EditText et = (EditText) findViewById(R.id.thresholdEntry);
        String threshold = et.getText().toString();
        editor.putString("threshold", threshold);
        editor.putBoolean("fahrenheit",fahrenheit);
        editor.putBoolean("celsius",celsius);
        editor.putBoolean("disable",disabled);

        editor.commit();
        Toast.makeText(this, "Threshold Saved",
                Toast.LENGTH_SHORT).show();
    }


    public void storePhone() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        EditText et = (EditText) findViewById(R.id.entry1);
        String number = et.getText().toString();
        editor.putString("number", number);
        editor.commit();
        Toast.makeText(this, "Target Saved",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String data = intent.getStringExtra("sms");

        if(data == null)
            setContentView(R.layout.control_page);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("number", "undefined");
        String thresText = prefs.getString("threshold","30");

        disabled = prefs.getBoolean("disable",true);
        fahrenheit = prefs.getBoolean("fahrenheit",false);
        celsius = prefs.getBoolean("celsius",false);


        Button b = (Button) findViewById(R.id.setThreshold);
        Button b1 = (Button) findViewById(R.id.storeOptions);
        Button b2 = (Button) findViewById(R.id.runQuery);
        Button b3 = (Button) findViewById(R.id.button3);

        b.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

        EditText et = (EditText) findViewById(R.id.entry1);
        et.setText(restoredText);

        EditText et2 = (EditText) findViewById(R.id.thresholdEntry);
        et2.setText(thresText);



        if (bcc == null)
            bcc = new BasicCoapClient(); // create a dummy client for autonomus messages

        if (data != null && bcc!=null) {
            bcc.processIntent(intent);
            smsincount++;
        }


        String version = getVersionInfo();
        TextView tv = (TextView) findViewById(R.id.softwareVersion);
        tv.setText(version);

        RadioButton tc = (RadioButton) findViewById(R.id.thresholdCelsius);
        RadioButton tf = (RadioButton) findViewById(R.id.thresholdFahrenheit);
        RadioButton di = (RadioButton) findViewById(R.id.disableAutoThreshold);

        tc.setOnClickListener(this);
        tf.setOnClickListener(this);
        di.setOnClickListener(this);

        tc.setChecked(celsius);
        tf.setChecked(fahrenheit);
        di.setChecked(disabled);
    }

    public void runQuery()
    {

        EditText et = (EditText) findViewById(R.id.entry1);
        String number = et.getText().toString();

        bcc = new BasicCoapClient(this, number);
        bcc.setRequestCode(CoapRequestCode.GET);
        bcc.init();
        bcc.runTestClient();
    }

    public void inSmsOutCount() {
        smsoutcount++;
    }

    public void runPut()
    {

        EditText et = (EditText) findViewById(R.id.entry1);
        String number = et.getText().toString();  //phone number

        EditText et2 = (EditText) findViewById(R.id.thresholdEntry);

        // (°F  -  32)  x  5/9

        String tempToSend="";
        String tempToStore = et2.getText().toString();

        if (disabled) {
         tempToSend = "-9999";
        }

        if (fahrenheit) {
            int c=0;
            int f=0;
            try {
                f=Integer.parseInt(tempToStore);
                c = (f-32)*5/9;
                tempToSend=""+c;
            } catch (Exception e) {

            }
        }

        if (celsius) {
            tempToSend = tempToStore;
        }

        bcc = new BasicCoapClient(this, number);
        bcc.setRequestCode(CoapRequestCode.PUT);
        bcc.setUrl("/TEMP "+tempToSend);
        bcc.init();
        bcc.runTestClient();
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


    @Override
    public void onPause()
    {

        super.onPause();
        Log.v(tag, "On pause");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.v(tag, "On resume");
    }

    private void setMobileDataEnabled(Context context, boolean enabled) throws Exception {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField.get(conman);
        final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String data;
        String target;

        data = intent.getStringExtra("sms");
        target = intent.getStringExtra("phone");

        // make sure we have a valid message

        if (data == null) {
            //finish();
            return;
        }

        if (target == null) {
            //finish();
            return;
        }

        smsincount++;

        String temp = bcc.processIntent(intent);

        if (temp == null) {
            //finish();
            return;
        }

        Log.v(tag, ""+temp);

        Intent newintent = new Intent(this, TempActivity.class);
        newintent.putExtra("temp", temp);

        startActivity(newintent);
    }




    public String getVersionInfo() {
        String strVersion = "Software Version:";

        PackageInfo packageInfo;
        try {
            packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(
                            getApplicationContext().getPackageName(),
                            0
                    );
            strVersion += packageInfo.versionName;
        } catch (Exception e) {
            strVersion += "Unknown";
        }

        return strVersion;
    }

    public void statsDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.useful_info);
        dialog.setTitle("Stats");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.smsinCount);
        text.setText(""+smsincount);

        text = (TextView) dialog.findViewById(R.id.smsoutCount);
        text.setText(""+smsoutcount);



        Button dialogButton = (Button) dialog.findViewById(R.id.okbutton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogButton = (Button) dialog.findViewById(R.id.clearbutton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsoutcount=0;
                smsincount=0;
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(300, 400);
    }

}
