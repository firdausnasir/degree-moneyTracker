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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnlogin, btnsignup, btnforgot;
    public static String email, password;
    private EditText inputPassword, inputEmail;
    public static FirebaseAuth auth;
    public static FirebaseUser email_after;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        btnsignup = findViewById(R.id.register);
        btnlogin = findViewById(R.id.login);
        btnforgot = findViewById(R.id.forgotpassword);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);

        btnsignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
                finish();
            }
        });
    }

    public void SignIn(){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Logging in...");

        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        if(email.isEmpty()){
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();
             return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Please enter a valid email");
            inputEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            inputPassword.setError("Provide your password");
            inputPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            inputPassword.setError("Minimum length of password should be 6");
            inputPassword.requestFocus();
            return;
        }

        progressDialog.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, Homepage.class);
                    intent.putExtra("email_main", email);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    progressDialog.dismiss();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null) {
            email_after = FirebaseAuth.getInstance().getCurrentUser();
            email = email_after.getEmail();
            Intent intent = new Intent (this, Homepage.class);
            intent.putExtra("email_main",email);
            startActivity(intent);
            finish();
        }
    }
}
