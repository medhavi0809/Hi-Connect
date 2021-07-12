package com.example.newvideoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;


public class SignUp extends AppCompatActivity {
    private EditText rPassword, email, password;
    private Button signup;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    /**
     * when activity is getting created, inflate UI and initialize UI elements
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        email = findViewById(R.id.email_address);
        password = findViewById(R.id.password);
        rPassword = findViewById(R.id.r_password);
        signup = findViewById(R.id.signup_button);
        mAuth = FirebaseAuth.getInstance();
        /**
         * Create progress dialog to show loading indication
         */
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        /**
         * handle button clicks
         */
        signup.setOnClickListener(v -> {
            /**
             * handle keyboard visibility
             */
            hideKeyboard();
            /**
             * check if email , password and repeat passwords are empty and if they are empty on submit button click
             * we are sending message Enter all data
             */
            if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || rPassword.getText().toString().isEmpty()){
                Toast.makeText(SignUp.this,"Enter all data", Toast.LENGTH_LONG).show();
            }
            /**
             * check whether password and repeat password matches
             * and if they doesn't matches we are sending message Password doesn't match
             */
            else if(!password.getText().toString().equals(rPassword.getText().toString())){
                Toast.makeText(SignUp.this,"Password doesn't match", Toast.LENGTH_LONG).show();
            }else{
                signupWithEmail(email.getText().toString(), password.getText().toString());
            }
        });
    }

    /**
     * after successfully entering details user will be redirected to login
     * @param view
     */
    public void signin(View view){
        startActivity(new Intent(SignUp.this, LoginActivity.class));
        finishAffinity();
    }

    /**
     * check whether sign up is successful or not
     * @param emailId
     * @param password
     */
    private void signupWithEmail(String emailId, String password){
        //please wait progress dialog appears
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailId, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressDialog.cancel();
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(SignUp.this, "Signup successful, kindly login",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, LoginActivity.class));
                        finishAffinity();
                    } else {
                        progressDialog.hide();
                        if(task.getException().getLocalizedMessage()!=null) {
                            Toast.makeText(SignUp.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUp.this, "Unknown error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
