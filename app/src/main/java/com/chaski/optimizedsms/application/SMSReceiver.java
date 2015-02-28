package com.chaski.optimizedsms.application;
/**
 *
 * This code substantially form
 * https://github.com/kbeckmann/smsr/blob/master/src/se/xil/smsr/SMSReceiver.java
 *
 * Other references are made on kickstarter.
 *
 *   No claim is made to the original authors work.
 *   This header covers the the code as this collection.
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

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

import java.util.ArrayList;

public class SMSReceiver extends BroadcastReceiver {
    private final String DEBUG_TAG = getClass().getSimpleName().toString();
    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Context mContext;
    private Intent mIntent;


    // Retrieve SMS
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;

      
        String action = intent.getAction();

        SharedPreferences prefs = context.getSharedPreferences(MainActivity.MY_PREFS_NAME, Context.MODE_PRIVATE);
        String restoredText = prefs.getString("number", "undefined");

        if(action.equals(ACTION_SMS_RECEIVED)){

            String address="", str = "";
            int contactId = -1;

            SmsMessage[] msgs = getMessagesFromIntent(mIntent);
            if (msgs != null) {
                for (int i = 0; i < msgs.length; i++) {
                    address = msgs[i].getOriginatingAddress();
                   // contactId = ContactsUtils.getContactId(mContext, address, "address");
                    str += msgs[i].getMessageBody().toString();
                   // str += HexDump.dumpHexString(msgs[i].getDisplayMessageBody().getBytes());
                   // str += "\n";
                }
            }   

            Log.i(DEBUG_TAG, "STRING:["+str+"]");


            if(!restoredText.contains(address))
            {
                Log.i(DEBUG_TAG,"got from our number");
            }

            // ---send a broadcast intent to update the SMS received in the
            // activity---
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
            //intent.setClassName("com.example.mike.myapplication", "com.example.mike.myapplication.MainActivity");
            intent.setClass(context, MainActivity.class);
            intent.putExtra("sms", str);
            intent.putExtra("phone",address);
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d(DEBUG_TAG, "Sending: "+str);
            context.startActivity(intent);
        }

    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }

    /**
    * The notification is the icon and associated expanded entry in the status
    * bar.
    */
    protected void showNotification(int contactId, String message) {
        //Display notification...
    }
    
    private static String SENT = "SMS_SENT";
    private static String DELIVERED = "SMS_DELIVERED";
    private static int MAX_SMS_MESSAGE_LENGTH = 160;

 // ---sends an SMS message to another device---
    public static void sendSMS(String phoneNumber, String message, Context mContext) {

        PendingIntent piSent = PendingIntent.getBroadcast(mContext, 0, new Intent(SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(mContext, 0, new Intent(DELIVERED), 0);
        SmsManager smsManager = SmsManager.getDefault();

        int length = message.length();          
        if(length > MAX_SMS_MESSAGE_LENGTH) {
            ArrayList<String> messagelist = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, messagelist, null, null);
        }
        else {
            smsManager.sendTextMessage(phoneNumber, null, message, piSent, piDelivered);
        }
    }
    
}