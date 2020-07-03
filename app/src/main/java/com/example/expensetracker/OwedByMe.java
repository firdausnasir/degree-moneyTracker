package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwedByMe extends AppCompatActivity {

    public double owedbyme;
    public double totalOwedByMe;
    public EditText input_amount_owedbyme, input_name_owedbyme; //edit field of amount and note

    String email;
    String owedbyme_total;
    String owedbyme_name;
    DatabaseReference databaseTransaction;
    DatabaseReference BalanceOwedByMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owedbyme);

        input_amount_owedbyme = findViewById(R.id.amount_owedbyme); //field amount
        input_name_owedbyme = findViewById(R.id.name_owedbyme);
        Button save_owedbyme = findViewById(R.id.add_owedbyme); //button save

        save_owedbyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get string from edit field
                owedbyme_total = input_amount_owedbyme.getText().toString().trim();
                owedbyme_name = input_name_owedbyme.getText().toString().trim();

                if(owedbyme_total.isEmpty()){
                    input_amount_owedbyme.setError("Enter an amount");
                    input_amount_owedbyme.requestFocus();
                    return;
                }

                if(owedbyme_name.isEmpty()){
                    input_name_owedbyme.setError("Enter a name");
                    input_name_owedbyme.requestFocus();
                    return;
                }

                email = MainActivity.email;
                email = email.replace(".", ",");

                databaseTransaction = FirebaseDatabase.getInstance().getReference("OwedByMeTransaction");
                owedbyme = Double.parseDouble(owedbyme_total);
                totalOwedByMe = Double.parseDouble(Debthomepage.totalOwedByMe) + owedbyme;

                OwedByMeTransaction owedbymeTransaction = new OwedByMeTransaction( email, owedbyme_name, String.valueOf(owedbyme));
                databaseTransaction.push().setValue(owedbymeTransaction);

                BalanceOwedByMe = FirebaseDatabase.getInstance().getReference("BalanceOwedByMe");
                OwedByMeTransaction owedByMeBalance = new OwedByMeTransaction(email, owedbyme_name, String.valueOf(totalOwedByMe));
                BalanceOwedByMe.child(email).setValue(owedByMeBalance);

                startActivity(new Intent(OwedByMe.this, Debthomepage.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(OwedByMe.this, Debthomepage.class));
        super.onBackPressed();
        finish();
    }
}
