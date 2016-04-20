package com.example.kaj75.fieldapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Kees on 20/04/2016.
 */
public class MessageHandler {

    public MessageHandler() {
    }

    /**
     * Generates a message block for the user.
     * Format:
         <LinearLayout
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_marginBottom="10dp">
             <LinearLayout
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:layout_weight="2"></LinearLayout>
             <LinearLayout
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:layout_weight="0.7">
                 <TextView
                 android:layout_width="match_parent"
                 android:layout_height="match_parent"
                 android:background="@drawable/textmessageUser"
                 android:paddingLeft="20dp"
                 android:paddingRight="20dp"
                 android:paddingTop="5dp"
                 android:text="Ik heb versterking nodig!"/>
             </LinearLayout>
         </LinearLayout>
     * @param act : current activity
     * @param message : the message that needs to be displayed
     */
    public void sendMessageUser(Activity act, String message){
        //Creating the containter layout
        LinearLayout containerLayout = new LinearLayout(act);
        // specifying vertical orientation
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //Setting margin bot
        linLayoutParam.bottomMargin = 10;
        //Adding layout params to the layout
        containerLayout.setLayoutParams(linLayoutParam);

        //Creating filler layout
        LinearLayout fillerLayout = new LinearLayout(act);
        // specifying vertical orientation
        fillerLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //Setting layout weight
        linLayoutParam.weight = 2;
        //Adding layout params to the layout
        fillerLayout.setLayoutParams(linLayoutParam);

        //Creating message layout
        LinearLayout messageLayout = new LinearLayout(act);
        // specifying vertical orientation
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        //Setting layout weight
        linLayoutParam.weight = 0.7f;
        //Adding layout params to the layout
        messageLayout.setLayoutParams(linLayoutParam);

        //Creating textview
        TextView textView = new TextView(act);
        textView.setText(message);
        Drawable background = act.getResources().getDrawable( R.drawable.textmessage_user );
        textView.setBackgroundDrawable(background);
        linLayoutParam.leftMargin = 20;
        linLayoutParam.rightMargin = 20;
        linLayoutParam.topMargin = 5;
        textView.setLayoutParams(linLayoutParam);
        messageLayout.addView(textView);

        //Adding layouts to the containerLayout
        containerLayout.addView(fillerLayout);
        containerLayout.addView(messageLayout);

        //Adding message to the messageBox
        LinearLayout messageView = (LinearLayout) act.findViewById(R.id.messageBox);
        messageView.addView(containerLayout);
    }

    /**
     * Generates a message block for the user.
     * Format:
         <LinearLayout
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_marginBottom="5dp">
             <LinearLayout
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:layout_weight="0.7">
                 <TextView
                 android:layout_width="match_parent"
                 android:layout_height="wrap_content"
                 android:background="@drawable/textmessage_central"
                 android:paddingLeft="20dp"
                 android:paddingRight="20dp"
                 android:paddingTop="5dp"
                 android:text="Wat voor versterking?"/>
             </LinearLayout>
             <LinearLayout
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:layout_weight="2"></LinearLayout>
         </LinearLayout>-->
     * @param act : current activity
     * @param message : the message that needs to be displayed
     */
    public void sendMessageCentralPoint(Activity act, String message){
        //Creating the containter layout
        LinearLayout containerLayout = new LinearLayout(act);
        // specifying vertical orientation
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //Setting margin bot
        linLayoutParam.bottomMargin = 10;
        //Adding layout params to the layout
        containerLayout.setLayoutParams(linLayoutParam);

        //Creating filler layout
        LinearLayout fillerLayout = new LinearLayout(act);
        // specifying vertical orientation
        fillerLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //Setting layout weight
        linLayoutParam.weight = 2;
        //Adding layout params to the layout
        fillerLayout.setLayoutParams(linLayoutParam);

        //Creating message layout
        LinearLayout messageLayout = new LinearLayout(act);
        // specifying vertical orientation
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        //Setting layout weight
        linLayoutParam.weight = 0.7f;
        //Adding layout params to the layout
        messageLayout.setLayoutParams(linLayoutParam);

        //Creating textview
        TextView textView = new TextView(act);
        textView.setText(message);
        Drawable background = act.getResources().getDrawable( R.drawable.textmessage_central );
        textView.setBackgroundDrawable(background);
        linLayoutParam.leftMargin = 20;
        linLayoutParam.rightMargin = 20;
        linLayoutParam.topMargin = 5;
        textView.setLayoutParams(linLayoutParam);
        messageLayout.addView(textView);

        //Adding layouts to the containerLayout
        containerLayout.addView(messageLayout);
        containerLayout.addView(fillerLayout);

        //Adding message to the messageBox
        LinearLayout messageView = (LinearLayout) act.findViewById(R.id.messageBox);
        messageView.addView(containerLayout);
    }
}
