package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static com.example.expensetracker.MainActivity.auth;
import static com.example.expensetracker.MainActivity.email;

public class Homepage extends AppCompatActivity {

    public static TextView total_txt, emailView;
    public static double total;
    public static String total_DB;

    String dateTime, homepage_email, total_str;
    DatabaseReference databaseBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Button income = findViewById(R.id.income);
        Button expense = findViewById(R.id.newexpense);
        Button trans_history = findViewById(R.id.transaction);
        Button debt = findViewById(R.id.debt);
        Button logout = findViewById(R.id.logout);
        total_txt = findViewById(R.id.balance);
        emailView = findViewById(R.id.email_view);

        final String emailID = MainActivity.email;

        emailView.setText(emailID);

        homepage_email = emailID.replace(".",",");

        databaseBalance = FirebaseDatabase.getInstance().getReference("BalanceTransaction");

        dateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        total_str = String.valueOf(total);
        total_txt.setText(total_str);

        databaseBalance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*BalanceTransaction balanceTransaction = dataSnapshot.getValue(BalanceTransaction.class);
                total_DB = balanceTransaction.getBalance();

                total_txt.setText(total_DB);*/

                total_DB = (String) dataSnapshot.child(homepage_email).child("balance").getValue();
                total_txt.setText(total_DB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        BalanceTransaction balanceTransaction2 = new BalanceTransaction(emailID, dateTime, total_DB);
//        databaseBalance.child(homepage_email).setValue(balanceTransaction2);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Homepage.this, Income.class));
                finish();
                Intent intent = new Intent (Homepage.this, Income.class);
                intent.putExtra("email_homepage",emailID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Homepage.this, Expense.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Homepage.this, MainActivity.class));
                finish();
            }
        });

        trans_history.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                finish();
                startActivity( new Intent(Homepage.this, TransactionHistory.class));
                finish();

            }
        });

        debt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Homepage.this, Debthomepage.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

            if(auth.getCurrentUser() == null){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
    }
}
