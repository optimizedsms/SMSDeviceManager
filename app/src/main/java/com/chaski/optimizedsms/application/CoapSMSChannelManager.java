package com.chaski.optimizedsms.application;

import org.ws4d.coap.interfaces.CoapChannelManager;
import org.ws4d.coap.interfaces.CoapClient;
import org.ws4d.coap.interfaces.CoapClientChannel;
import org.ws4d.coap.interfaces.CoapMessage;
import org.ws4d.coap.interfaces.CoapServer;
import org.ws4d.coap.interfaces.CoapServerChannel;
import org.ws4d.coap.interfaces.CoapSocketHandler;

import java.net.InetAddress;

/**
 * Created by mike on 1/11/15.
 * *       [University of Rostock copyright and license terms apply]
 */



public class CoapSMSChannelManager implements CoapChannelManager {

    static CoapSMSChannelManager instance = null;
    int messageId = 0;

    public synchronized static CoapSMSChannelManager getInstance() {
        if (instance == null) {
            instance = new CoapSMSChannelManager();
        }
        return instance;
    }


    @Override
    public int getNewMessageID() {
        return messageId++;
    }

    @Override
    public CoapServerChannel createServerChannel(CoapSocketHandler socketHandler, CoapMessage message, InetAddress addr, int port) {
        return null;
    }

    @Override
    public void createServerListener(CoapServer serverListener, int localPort) {
    }



    public CoapClientChannel connect(CoapClient client, MainActivity _activity, String _addr) {
        CoapSMSClientChannel channel = new CoapSMSClientChannel(this, _activity, _addr);
        return channel;
    }

    @Override
    public CoapClientChannel connect(CoapClient client, InetAddress addr, int port) {
        return null;
    }

    @Override
    public void setMessageId(int globalMessageId) {

    }

    @Override
    public void initRandom() {

    }
}
