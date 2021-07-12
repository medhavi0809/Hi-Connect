package com.example.newvideoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends Activity {
    Button login_btn;
    private TextView createaccount_tv;
    private EditText emailAddress, password;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    /**
     * when activity is getting created, inflate UI and initialize UI elements
     * @param savedInstanceState for restoring UI states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        createaccount_tv = findViewById(R.id.createaccount_tv);
        login_btn = findViewById(R.id.login_btn);
        emailAddress = findViewById(R.id.email_address);
        password = findViewById(R.id.password);
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
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * handle keyboard visibility
                 */
                hideKeyboard();
                String email = emailAddress.getText().toString();
                String pass = password.getText().toString();
                /**
                 * Check email and password fields, and validate input
                 */
                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter email and password", Toast.LENGTH_LONG).show();
                } else {
                    //show loading indication via ProgressDialog
                    progressDialog.show();
                    signInWithEmailPassword(email, pass);
                }
            }
        });
        /**
         * handle signup button clicks
         */
        createaccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Start signup activity on button click
                 */
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Method to be called in when sigin via firebase is required, with below params.
     * @param emailAddressText
     * @param passwordText
     */
    private void signInWithEmailPassword(String emailAddressText, String passwordText){
        /**
         * start the signin call via FirebaseAuth, and wait for callbacks from firebase
         */
        mAuth.signInWithEmailAndPassword(emailAddressText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("LogNotTimber")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Hide the progress dialog
                        progressDialog.hide();
                        //check if the task was executed successfully, and land user to MainActivity
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWiths=Email:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.getException());
                            if(task.getException().getLocalizedMessage()!=null) {
                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this, "Unknown error",
                                        Toast.LENGTH_SHORT).show();
                            }
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