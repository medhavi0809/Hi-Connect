package com.example.newvideoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    /**
     * This object encapsulates all the options that can be tweaked when joining a conference
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    /**
     * handle button clicks
     */
    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.conferenceName);
        /**
         * we are taking input(room name) from user
         */
        String text = editText.getText().toString();
        /**
         * if text length is greater than zero a room is created
         */
        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .build();
            /**
             * launch method takes care of required intent and passing the options
             */
            JitsiMeetActivity.launch(this, options);
        }
    }
}