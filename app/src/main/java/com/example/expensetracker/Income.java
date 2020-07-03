package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class Income extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener  {

    public double income;
    public EditText input_amount_income, input_note_income; //edit field of amount and note
    public TextView date; //textView of chosen date
    public double total_fromIncome;

    String email;
    String income_total;
    String income_note;
    String income_currDate;
    String income_category;
    String dateTime;
    String income_DB;
    DatabaseReference databaseTransaction;
    DatabaseReference databaseBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        input_amount_income = findViewById(R.id.amount_income); //field amount
        input_note_income = findViewById(R.id.note_income);
        Button calendar_income = findViewById(R.id.dateButtonIncome);
        Button save_income = findViewById(R.id.add_income); //button save
        final Spinner spinner = findViewById(R.id.category_income);

        dateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_income, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        save_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get string from edit field
                income_total = input_amount_income.getText().toString().trim();
                income_note = input_note_income.getText().toString().trim();

//                Intent intent = getIntent(); //get intent from homepage
//                final String income_emailID = intent.getStringExtra("email_homepage"); //get intent from homepage

                final String income_emailID = MainActivity.email;
                email = income_emailID;
                email = email.replace(".", ",");

                //check if edit field is empty
                if(income_total.isEmpty()){
                    input_amount_income.setError("Amount is required");
                    input_amount_income.requestFocus();
                    return;
                }
                if(income_note.isEmpty()){
                    input_note_income.setError("Note is required");
                    input_note_income.requestFocus();
                    return;
                }

                //if got, proceed
                databaseTransaction = FirebaseDatabase.getInstance().getReference().child("IncomeTransaction");
                income = Double.parseDouble(income_total);
                total_fromIncome = Double.parseDouble(Homepage.total_DB) + income;
                income_DB = String.valueOf(total_fromIncome);

                databaseBalance = FirebaseDatabase.getInstance().getReference().child("BalanceTransaction");

                IncomeTransaction incomeTransaction = new IncomeTransaction(email, income_category, income_note, income_currDate, String.valueOf(income));
                databaseTransaction.push().setValue(incomeTransaction);

                BalanceTransaction balanceTransaction = new BalanceTransaction(email, dateTime, income_DB);
                databaseBalance.child(email).setValue(balanceTransaction);

                startActivity(new Intent(Income.this, Homepage.class));
                finish();
            }
        });


        calendar_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        income_currDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        date = findViewById(R.id.date_income);
        date.setText(income_currDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        income_category = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), income_category, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Income.this, Homepage.class));
        super.onBackPressed();
        finish();
    }
}
