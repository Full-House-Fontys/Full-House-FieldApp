package com.example.kaj75.fieldapp;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String ip = "192.168.1.101";
    private int isOnMission = -1;
    private ArrayList<String> lastMessages;

    static final int socketServerPORT = 8080;

    private Queue<CommunicationMessage> messageQueue;
    private ServerSocket socket;
    private Thread check;

    public int getIsOnMission() {
        return isOnMission;
    }

    public void setLastMessages(ArrayList<String> lastMessages) {
        this.lastMessages = lastMessages;
    }

    public ArrayList<String> getLastMessages() {
        return lastMessages;
    }

    Client(){
        lastMessages = new ArrayList<>();
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

        messageQueue = new ConcurrentLinkedQueue<CommunicationMessage>();
        socket = new ServerSocket(socketServerPORT);

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
            CommunicationRequest communicationRequest = consumeRequest();

            if (communicationRequest != null)
                handle(communicationRequest);
        }
    }

    private void handle(CommunicationRequest communicationRequest){
        switch (communicationRequest.getUrl()){
            case "login":
                if(communicationRequest.getPayload().equals("-1")) {
                    isOnMission = 0;
                }else {
                    isOnMission = Integer.parseInt(communicationRequest.getPayload());
                }
                break;
            case "getMessages":
                //last 5 messages
                ArrayList<String> newMessages = new ArrayList<>();
                newMessages.addAll(new ArrayList<String>(Arrays.asList(communicationRequest.getPayload().split("///"))));
                for(String newString : newMessages){
                    if(!lastMessages.contains(newString)){
                        lastMessages.add(newString);
                    }
                }
                break;
            default:
                System.out.println(communicationRequest.getUrl()+communicationRequest.getPayload());
        }
    }

    /**
     * Gets the latest network message as a request type
     * @return The latest network request if there is one
     */
    private CommunicationRequest consumeRequest() {
        CommunicationMessage communicationMessage = consumeMessage();
        if (communicationMessage != null)
            return new CommunicationRequest(communicationMessage);
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
        send("login/"+userString+":"+passString, ip);
        //send("requestBackup/"+"vuur:snel:0:0:3:0:4", ip);
    }

    public void getMessage() {
        send("getMessages/", ip);
    }

    public void sendMessage(String message) {
        send("sendMessage/"+message+isOnMission, ip);
    }
}
