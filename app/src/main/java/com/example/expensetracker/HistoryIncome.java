package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class HistoryIncome extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_income);

        recyclerView = findViewById(R.id.listIncome);
        new FirebaseDataHelperIncome().readIncome(new FirebaseDataHelperIncome.DataStatus() {
            @Override
            public void DataIsLoaded(List<IncomeTransaction> IncomeTransaction, List<String> keys) {
                new IncomeRecyclerViewConfig().setConfig(recyclerView, HistoryIncome.this, IncomeTransaction, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HistoryIncome.this, TransactionHistory.class));
        finish();
    }
}
