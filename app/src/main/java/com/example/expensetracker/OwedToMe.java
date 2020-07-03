package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwedToMe extends AppCompatActivity {

    public double owedtome;
    public double totalOwedToMe;
    public EditText input_amount_owedtome, input_name_owedtome; //edit field of amount and name

    String email;
    String owedtome_total;
    String owedtome_name;
    DatabaseReference databaseTransaction;
    DatabaseReference BalanceOwedToMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owedtome);

        Button save_owedtome = findViewById(R.id.add_owedtome);
        input_amount_owedtome = findViewById(R.id.amount_owedtome);
        input_name_owedtome = findViewById(R.id.name_owedtome);

        save_owedtome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owedtome_total = input_amount_owedtome.getText().toString().trim();
                owedtome_name = input_name_owedtome.getText().toString().trim();

                if(owedtome_total.isEmpty()){
                    input_amount_owedtome.setError("Enter an amount");
                    input_amount_owedtome.requestFocus();
                    return;
                }

                if(owedtome_name.isEmpty()){
                    input_name_owedtome.setError("Enter a name");
                    input_name_owedtome.requestFocus();
                    return;
                }

                email = MainActivity.email;
                email = email.replace(".", ",");

                databaseTransaction = FirebaseDatabase.getInstance().getReference("OwedToMeTransaction");
                owedtome = Double.parseDouble(owedtome_total);
                totalOwedToMe = Double.parseDouble(Debthomepage.totalOwedToMe) + owedtome;

                OwedToMeTransaction owedtomeTransaction = new OwedToMeTransaction(email, owedtome_name, String.valueOf(owedtome));
                databaseTransaction.push().setValue(owedtomeTransaction);

                BalanceOwedToMe = FirebaseDatabase.getInstance().getReference("BalanceOwedToMe");
                OwedToMeTransaction owedToMeBalance = new OwedToMeTransaction(email, owedtome_name, String.valueOf(totalOwedToMe));
                BalanceOwedToMe.child(email).setValue(owedToMeBalance);

                startActivity(new Intent(OwedToMe.this, Debthomepage.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(OwedToMe.this, Debthomepage.class));
        super.onBackPressed();
        finish();
    }
}
