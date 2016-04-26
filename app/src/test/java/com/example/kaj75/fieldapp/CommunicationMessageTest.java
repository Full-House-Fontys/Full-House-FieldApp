package com.example.kaj75.fieldapp;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kees on 26/04/2016.
 */
public class CommunicationMessageTest {

    @Test
    public void testGetText() throws Exception {
        CommunicationMessage communicationMessage = new CommunicationMessage("Hallo", "Karel", "Hans");
        Assert.assertEquals("Text is not correct", "Hallo", communicationMessage.getText());
    }
}