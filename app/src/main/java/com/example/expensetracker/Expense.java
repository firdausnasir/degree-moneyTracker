package com.example.expensetracker;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Calendar;

public class Expense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener  {

    public double expense;
    public EditText input_amount_expense, input_note_expense; //edit field of amount and note
    public TextView date; //textView of chosen date
    public double total_fromExpense;

    String email;
    String expense_total;
    String expense_note;
    String expense_currDate;
    String expense_category;
    String dateTime;
    String expense_DB;
    DatabaseReference databaseTransaction;
    DatabaseReference databaseBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Button save_expense = findViewById(R.id.addexpense);
        input_amount_expense = findViewById(R.id.amount_expense);
        input_note_expense = findViewById(R.id.note_expense);
        Button calendar = findViewById(R.id.dateButton);
        final Spinner spinner = findViewById(R.id.category_expense);

        dateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_expense, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        save_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expense_total = input_amount_expense.getText().toString().trim();
                expense_note = input_note_expense.getText().toString().trim();

                final String expense_emailID = MainActivity.email;
                email = expense_emailID;
                email = email.replace(".", ",");

                if(expense_total.isEmpty()){
                    input_amount_expense.setError("Amount is required");
                    input_amount_expense.requestFocus();
                    return;
                }
                if(expense_note.isEmpty()){
                    input_note_expense.setError("Note is required");
                    input_note_expense.requestFocus();
                    return;
                }

                databaseTransaction = FirebaseDatabase.getInstance().getReference().child("ExpenseTransaction");
                expense = Double.parseDouble(expense_total);
                total_fromExpense = Double.parseDouble(Homepage.total_DB) - expense;
                expense_DB = String.valueOf(total_fromExpense);

                databaseBalance = FirebaseDatabase.getInstance().getReference().child("BalanceTransaction");

                ExpenseTransaction expenseTransaction = new ExpenseTransaction(email, expense_category, expense_currDate, expense_note, String.valueOf(expense));
                databaseTransaction.push().setValue(expenseTransaction);

                BalanceTransaction balanceTransaction = new BalanceTransaction(email, dateTime, expense_DB);
                databaseBalance.child(email).setValue(balanceTransaction);

                startActivity(new Intent(Expense.this, Homepage.class));
                finish();
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
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
        expense_currDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        date = findViewById(R.id.date);
        date.setText(expense_currDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        expense_category = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), expense_category, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Expense.this, Homepage.class));
        super.onBackPressed();
        finish();
    }
}
