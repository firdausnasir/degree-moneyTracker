package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    public EditText inputPassword, inputEmail;
    public static String initial = "0.00";
    private Button buttonlogin, buttonsignup;
    private FirebaseAuth auth;

    DatabaseReference databaseReference;
    DatabaseReference BalanceOwedByMe;
    DatabaseReference BalanceOwedToMe;
    String email, password;
    String dateandTime;
    String emailID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonlogin = findViewById(R.id.login2);
        buttonsignup = findViewById(R.id.register2);
        inputPassword = findViewById(R.id.password);
        inputEmail = findViewById(R.id.email);

        databaseReference = FirebaseDatabase.getInstance().getReference("BalanceTransaction");
        BalanceOwedByMe = FirebaseDatabase.getInstance().getReference("BalanceOwedByMe");
        BalanceOwedToMe = FirebaseDatabase.getInstance().getReference("BalanceOwedToMe");
        dateandTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        auth = FirebaseAuth.getInstance();

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });


        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    public void addUser(){
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Registering...");

        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();

        if (email.isEmpty()) {
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Provide a valid email address");
            inputEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            inputPassword.setError("Provide your password");
            inputPassword.requestFocus();
            return;
        }

        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            inputPassword.setError("Minimum length of password should be 6");
            inputPassword.requestFocus();
            return;
        }

        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    auth.getInstance().signOut();
                    emailID = email.replace(".",",");
                    BalanceTransaction balanceTransaction = new BalanceTransaction(emailID, dateandTime, initial);
                    OwedByMeTransaction owedByMeTransaction = new OwedByMeTransaction(emailID, null, initial);
                    OwedToMeTransaction owedToMeTransaction = new OwedToMeTransaction(emailID, null, initial);
                    databaseReference.child(emailID).setValue(balanceTransaction);
                    BalanceOwedByMe.child(emailID).setValue(owedByMeTransaction);
                    BalanceOwedToMe.child(emailID).setValue(owedToMeTransaction);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }
}
