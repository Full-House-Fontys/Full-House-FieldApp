package com.example.kaj75.fieldapp;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Kees on 25/04/2016.
 */
public class Client {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    private String ip = "145.93.173.123";
    private int isUser = -1;

    static final int socketServerPORT = 8080;

    private Queue<CommunicationMessage> messageQueue;
    private ServerSocket socket;
    private Thread check;

    public int getIsUser() {
        return isUser;
    }

    Client(){
        try {
            startListeners();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread listener = new Thread(new Runnable() {
            @Override
            public void run() {
                listen();
            }
        });
        listener.start();
    }

    public boolean send(String message, String receiver) {
        try {
            Socket socket = new Socket(receiver, 8080);//this.getPort());

            // Get input streams
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeBytes(message);

            outputStream.close();
            socket.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void startListeners() throws IOException {
        //stopListeners();

        // Initialize all variables
        if(messageQueue == null) {
            messageQueue = new ConcurrentLinkedQueue<CommunicationMessage>();
        }
        if(socket == null) {
            socket = new ServerSocket(socketServerPORT);
        }
        // Create runnable
        check = new Thread(new Runnable(){
            @Override
            public void run() {
                constantCheck();
            }
        });

        // Execute
        check.start();
    }

    /**
     * Clears all local variables and stops listening
     */
    public void stopListeners() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        check.stop();
        messageQueue.clear();
    }

    private void constantCheck() {
        try {
            while (true) {
                Socket clientSocket = this.socket.accept();

                String full = recv(clientSocket);

                // Read it into an object
                CommunicationMessage communicationMessage = new CommunicationMessage(full, clientSocket.getInetAddress().toString(), clientSocket.getLocalSocketAddress().toString());

                synchronized (this) {
                    messageQueue.add(communicationMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        while (true) {
            CommunicationMessage communicationMessage = consumeRequest();

            if (communicationMessage != null)
                handle(communicationMessage);
        }
    }

    private void handle(CommunicationMessage communicationMessage){
        switch (communicationMessage.getText()){
            case "true":
                isUser = 1;
                break;
            case "false":
                isUser = 0;
                break;
            default:
                System.out.println(communicationMessage.getText());
        }
    }

    /**
     * Gets the latest network message as a request type
     * @return The latest network request if there is one
     */
    private CommunicationMessage consumeRequest() {
        CommunicationMessage communicationMessage = consumeMessage();
        if (communicationMessage != null)
            return communicationMessage;
        else
            return null;
    }

    /**
     * Returns a message item
     * @return NetworkMessage that was first in the queue. Null if there was none
     */
    private CommunicationMessage consumeMessage() {
        synchronized (this) {
            return messageQueue.poll();
        }
    }

    private String recv(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String full = "";

        // Read the data into a string
        while ((line = bufferedReader.readLine()) != null) {
            full += line.trim();
        }
        return full;
    }

    public void login(String userString, String passString) {
        send("login/"+userString+":"+passString, "145.93.173.123");
    }
}
