package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    public EditText inputEmail;
    public Button resetButton;
    private FirebaseAuth auth;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = findViewById(R.id.email_forgot);
        resetButton = findViewById(R.id.btn_reset);
        auth = FirebaseAuth.getInstance();

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("Verifying...");

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    inputEmail.setError("Please enter your registered email");
                    inputEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.setError("Please enter valid email address");
                    inputEmail.requestFocus();
                    return;
                }
                progressDialog.show();
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ForgotPassword.this, MainActivity.class));
        super.onBackPressed();
        finish();
    }
}
