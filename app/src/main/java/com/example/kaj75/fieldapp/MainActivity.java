package com.example.kaj75.fieldapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    Client client;
    LinearLayout loginButton, requestSupportListener;

    MessageHandler messageHandler = new MessageHandler();
    Thread sendMes;
    int count =0;
    boolean skip = false;

    boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //werkt misschien?
        client = new Client();
        sendMes = new Thread();
        loginButton = (LinearLayout) findViewById(R.id.btnLogin);
        requestSupportListener = (LinearLayout) findViewById(R.id.btnEditTeam);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
        requestSupportListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSupport(v);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_message) {
            if(isLoggedIn){
                //TODO navigate right
                findViewById(R.id.loginContainer).setVisibility(View.GONE);
                findViewById(R.id.messageContainer).setVisibility(View.VISIBLE);
                findViewById(R.id.teamContainer).setVisibility(View.GONE);
            }
            return true;
        }
        if (id == R.id.action_team) {
            if(isLoggedIn){
                //TODO navigate right
                findViewById(R.id.loginContainer).setVisibility(View.GONE);
                findViewById(R.id.messageContainer).setVisibility(View.GONE);
                findViewById(R.id.teamContainer).setVisibility(View.VISIBLE);
            }
            return true;
        }
        if (id == R.id.action_log_out) {
            //TODO navigate right
            findViewById(R.id.loginContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.messageContainer).setVisibility(View.GONE);
            findViewById(R.id.teamContainer).setVisibility(View.GONE);
            isLoggedIn = false;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        EditText editTextUser = (EditText) findViewById(R.id.etUsername);
        EditText editTextPass = (EditText) findViewById(R.id.etPassword);
        final String userString = editTextUser.getText().toString();
        final String passString = editTextPass.getText().toString();

        Thread login = new Thread(new Runnable() {
            @Override
            public void run() {
                client.login(userString, passString);
            }
        });
        login.start();

        while(client.getIsOnMission() == -1){
            count += 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count == 5){
                skip = true;
                break;
            }
        }
        if(!skip) {
            if (client.getIsOnMission() != 0) {
                getMessages();
                findViewById(R.id.loginContainer).setVisibility(View.GONE);
                findViewById(R.id.messageContainer).setVisibility(View.VISIBLE);
                fillMessageBox();
            } else {
                editTextPass.setText("");
                editTextUser.setText("");
            }
        }else {
            skip = false;
            count = 0;
            editTextPass.setText("");
            editTextUser.setText("");
        }
    }

    private void fillMessageBox() {
        while(client.getLastMessages().isEmpty()){
            Thread.yield();
        }
        for(String message: client.getLastMessages()){
            StringBuilder messageBuild = new StringBuilder(message);
            messageBuild.setLength(messageBuild.length()-1);
            if(message.charAt(message.length()-1)=='0') {
                messageHandler.sendMessageCentralPoint(this, messageBuild.toString());
            }else if(message.charAt(message.length()-1)=='1'){
                messageHandler.sendMessageUser(this, messageBuild.toString());
            }
        }
    }

    private void getMessages() {
        Thread ss = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("NOI");
                client.getMessage();
            }
        });
        ss.start();
    }

    public void sendMessage(View view){

        EditText editText = (EditText) findViewById(R.id.messageTextBox);
        //ScrollView scrollView = (ScrollView) findViewById(R.id.scroLLView);
        final String sendMesText = editText.getText().toString();
        messageHandler.clearMessages(this);
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                client.sendMessage(sendMesText);
            }
        });
        send.start();
        Thread get = new Thread(new Runnable() {
            @Override
            public void run() {
                getMessages();
            }
        });
        get.start();
        fillMessageBox();
    }

    public void requestSupport(View v){
        //request send
    }
}
