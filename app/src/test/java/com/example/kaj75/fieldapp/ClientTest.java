package com.example.kaj75.fieldapp;

import android.os.Debug;
import android.util.Log;

import junit.framework.Assert;

import java.net.Inet4Address;

import static org.junit.Assert.*;

/**
 * Created by Kees on 26/04/2016.
 */
public class ClientTest {

    Client client = new Client();
    String ipAdress;

    @org.junit.Before
    public void setUp() throws Exception {
        ipAdress = Inet4Address.getLocalHost().toString().substring(Inet4Address.getLocalHost().toString().indexOf("/") + 1);
    }

    @org.junit.Test
    public void testGetIsUser() throws Exception {
        Assert.assertEquals("User is not -1", -1, client.getIsUser());
    }

    @org.junit.Test
    public void testSend() throws Exception {
        Assert.assertTrue(client.send("Hallo", ipAdress));
    }

    @org.junit.Test
    public void testStopListeners() throws Exception {
        client.stopListeners();
    }

    @org.junit.Test
    public void testLogin() throws Exception {
        client.login("Kees", "Passwoord");
    }
}