package com.example.newvideoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    /**
     * Launch splash activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            /**
             * we get the current state of user that whether he is a new user or not
             */
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                /**
                 * if user has already registered then  he is directed to jitsi activity
                 */
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }else{
                /**
                 * if the logged in user launches application then he is redirected to login activity
                 */
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finishAffinity();
        }, 3000);
    }
}