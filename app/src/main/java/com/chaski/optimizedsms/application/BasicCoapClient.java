package com.chaski.optimizedsms.application;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import org.ws4d.coap.interfaces.CoapClient;
import org.ws4d.coap.interfaces.CoapClientChannel;
import org.ws4d.coap.interfaces.CoapRequest;
import org.ws4d.coap.interfaces.CoapResponse;
import org.ws4d.coap.messages.BasicCoapResponse;
import org.ws4d.coap.messages.CoapRequestCode;

import java.io.UnsupportedEncodingException;

/**
 * @author Christian Lerche <christian.lerche@uni-rostock.de>
 *       [University of Rostock copyright and license terms apply]
 */

public class BasicCoapClient implements CoapClient {


    static int counter = 0;
    private final String tag = getClass().getSimpleName().toString();
    CoapSMSChannelManager channelManager = null;
    CoapClientChannel clientChannel = null;

    String address = "";
    String url = "/temp";

    MainActivity context;
    CoapRequestCode code = CoapRequestCode.GET;
    String val;

    public void setRequestCode(CoapRequestCode _code) {
        code = _code;
    }

    public void setUrl(String _url) {
        url = _url;
    }

    public void setVal(String _val) {
        val = _val;
    }

    public BasicCoapClient() {
        Log.i(tag,"BasicCoapClientCreated");
    }

    public BasicCoapClient(MainActivity _context, String _number) {
    	counter = 1;
    	context = _context;
        address = _number;
        channelManager = CoapSMSChannelManager.getInstance();
    }

    
    public void init () {
    	channelManager = CoapSMSChannelManager.getInstance();
    }
    
    public void runTestClient(){
    	try {
			clientChannel = channelManager.connect(this, context,address);
            CoapRequest coapRequest = clientChannel.createRequest(true, code);
			coapRequest.setUriPath(url);
			byte[] buf = coapRequest.serialize();
			Log.i(tag, HexDump.dumpHexString(buf));
			clientChannel.sendMessage(coapRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
		System.out.println("Connection Failed");
	}

	@Override
	public void onResponse(CoapClientChannel channel, CoapResponse response) {
		String str="";
        int temp;

		Log.i(tag, "Received response");
		byte[] payload = response.getPayload();

		try {
			str = new String(payload, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
            String error = e.toString();
			Log.e(tag, error);
		}
	}



    String processIntent(Intent intent) {
        String data;
        String target;


        data = intent.getStringExtra("sms");
        target = intent.getStringExtra("phone");

        Log.v(tag,"data was received");
        IetfCoAPOptimizedSMS osms = new IetfCoAPOptimizedSMS();
        osms.loadMap();;
        byte[] buffer = data.getBytes();
        byte[] message = osms.bufferToMessage(buffer);
        CoapResponse response = null;

        try {
            response = new BasicCoapResponse(message, message.length);
        }
        catch (Exception e) {
            return null;
        }
        String str=null;
        int temp;

        Log.v(tag, "Received response");
        byte[] payload = response.getPayload();

        try {
            str = new String(payload, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        //onponse(null, response);

        return str;
    }



    private void startMainActivity(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(context.getPackageName());
        context.startActivity(intent);
    }
}
