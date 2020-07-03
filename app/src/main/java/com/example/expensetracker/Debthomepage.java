package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.expensetracker.MainActivity.auth;

public class Debthomepage extends AppCompatActivity {

    public static TextView total_txt1, total_txt2, emailView;
    public static double total1, total2;
    public String OwedToMe_DB;
    public static String totalOwedToMe;
    public static String totalOwedByMe;

    String debthomepage_email;
    DatabaseReference databaseOwedToMe;
    DatabaseReference databaseOwedByMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debthomepage);

        Button owedtome = findViewById(R.id.owedtome);
        Button owedbyme = findViewById(R.id.owedbyme);
        Button addowedtome = findViewById(R.id.addowedtome);
        Button addowedbyme = findViewById(R.id.addowedbyme);
        total_txt1 = findViewById(R.id.total1);
        total_txt2 = findViewById(R.id.total2);
        emailView = findViewById(R.id.email_view);

        final String emailID = MainActivity.email;

        emailView.setText(emailID);

        debthomepage_email = emailID.replace(".",",");

        databaseOwedToMe = FirebaseDatabase.getInstance().getReference("BalanceOwedToMe");
        databaseOwedByMe = FirebaseDatabase.getInstance().getReference("BalanceOwedByMe");

        String total_str1 = String.valueOf(total1);
        String total_str2 = String.valueOf(total2);
        total_txt1.setText(total_str1);
        total_txt2.setText(total_str2);

        databaseOwedToMe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalOwedToMe = (String) dataSnapshot.child(debthomepage_email).child("amount").getValue();
                total_txt1.setText(totalOwedToMe);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseOwedByMe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalOwedByMe = (String) dataSnapshot.child(debthomepage_email).child("amount").getValue();
                total_txt2.setText(totalOwedByMe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        owedbyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Homepage.this, Income.class));
                finish();
                Intent intent = new Intent(Debthomepage.this, ButtonOwedByMe.class);
                intent.putExtra("email_debthomepage",emailID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        owedtome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Debthomepage.this, ButtonOwedToMe.class));
                finish();
            }
        });

        addowedtome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Debthomepage.this, OwedToMe.class));
                finish();
            }
        });

        addowedbyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Homepage.this, Income.class));
                finish();
                Intent intent = new Intent(Debthomepage.this, OwedByMe.class);
                intent.putExtra("email_debthomepage",emailID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(Debthomepage.this, Homepage.class));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Debthomepage.this, Homepage.class));
        super.onBackPressed();
        finish();
    }
}
