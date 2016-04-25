package com.example.kaj75.fieldapp;

/**
 * Created by kaj75 on 20-4-2016.
 */
public class CommunicationMessage {
    private String text;
    private String sender;
    private String receiver;

    public CommunicationMessage(String text, String sender, String receiver) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }
}
