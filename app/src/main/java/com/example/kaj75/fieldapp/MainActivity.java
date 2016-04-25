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
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    MessageHandler messageHandler = new MessageHandler();
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //werkt misschien?

        scrollView = (ScrollView) findViewById(R.id.scrollView);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        findViewById(R.id.loginContainer).setVisibility(View.GONE);
        findViewById(R.id.messageContainer).setVisibility(View.VISIBLE);
    }

    public void sendMessage(View view){
        EditText editText = (EditText) findViewById(R.id.messageTextBox);
        String message = editText.getText().toString();
        editText.setText("");
        messageHandler.sendMessageUser(this, message);
        messageHandler.sendMessageCentralPoint(this, "...");

        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
