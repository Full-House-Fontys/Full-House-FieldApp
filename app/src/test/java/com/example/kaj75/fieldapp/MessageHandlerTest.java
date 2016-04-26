package com.example.kaj75.fieldapp;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kees on 26/04/2016.
 */
public class MessageHandlerTest {

    MessageHandler messageHandler;
    MainActivity main;

    @Before
    public void setUp() throws Exception {
        main = new MainActivity();
        messageHandler = new MessageHandler();
    }

    @Test
    public void testSendMessageUser() throws Exception {
        messageHandler.sendMessageUser(main, "Hallo wereld");
    }

    @Test
    public void testSendMessageCentralPoint() throws Exception {
        messageHandler.sendMessageCentralPoint(main, "Hallo terug");
    }
}