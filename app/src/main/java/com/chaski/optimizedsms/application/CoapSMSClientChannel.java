package com.chaski.optimizedsms.application;

import android.util.Log;

import org.ws4d.coap.interfaces.CoapClientChannel;
import org.ws4d.coap.interfaces.CoapMessage;
import org.ws4d.coap.interfaces.CoapRequest;
import org.ws4d.coap.messages.BasicCoapRequest;
import org.ws4d.coap.messages.CoapBlockOption;
import org.ws4d.coap.messages.CoapRequestCode;
import org.ws4d.coap.interfaces.CoapChannelManager;

import org.ws4d.coap.messages.CoapPacketType;


import java.net.InetAddress;



/**
 * Created by mike on 1/11/15.
 * *       [University of Rostock copyright and license terms apply]
 */

public class CoapSMSClientChannel implements CoapClientChannel{

    MainActivity context;
    String address = "";
    private final String DEBUG_TAG = getClass().getSimpleName().toString();

    CoapChannelManager channelManager = null;

    public CoapSMSClientChannel(CoapSMSChannelManager channelManager, MainActivity _context, String _address) {
        context = _context;
        address = _address;
        channelManager = CoapSMSChannelManager.getInstance();
    }

    @Override
    public CoapRequest createRequest(boolean reliable, CoapRequestCode requestCode) {

        channelManager = CoapSMSChannelManager.getInstance();

        BasicCoapRequest msg = new BasicCoapRequest(
                reliable ? CoapPacketType.CON : CoapPacketType.NON, requestCode,
                channelManager.getNewMessageID());
        msg.setChannel(this);
        return msg;
    }

    @Override
    public void setTrigger(Object o) {

    }

    @Override
    public Object getTrigger() {
        return null;
    }

    @Override
    public void sendMessage(CoapMessage msg) {
        IetfCoAPOptimizedSMS osms = new IetfCoAPOptimizedSMS() ;
        osms.loadMap();
        byte[] buffer = osms.messageToBuffer(msg.serialize());
        byte[] test = osms.bufferToMessage(buffer);
        Log.v(DEBUG_TAG, HexDump.dumpHexString(test, 0, test.length, 0));
        String outStr = new String(buffer);
        SMSReceiver.sendSMS(address, outStr, context);
        context.inSmsOutCount();
    }

    @Override
    public void close() {

    }

    @Override
    public InetAddress getRemoteAddress() {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public void handleMessage(CoapMessage message) {

    }

    @Override
    public void lostConnection(boolean notReachable, boolean resetByServer) {

    }

    @Override
    public CoapBlockOption.CoapBlockSize getMaxReceiveBlocksize() {
        return null;
    }

    @Override
    public void setMaxReceiveBlocksize(CoapBlockOption.CoapBlockSize maxReceiveBlocksize) {

    }

    @Override
    public CoapBlockOption.CoapBlockSize getMaxSendBlocksize() {
        return null;
    }

    @Override
    public void setMaxSendBlocksize(CoapBlockOption.CoapBlockSize maxSendBlocksize) {

    }
}
