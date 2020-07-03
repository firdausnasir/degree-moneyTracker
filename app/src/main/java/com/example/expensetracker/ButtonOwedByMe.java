package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ButtonOwedByMe extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttonowedbyme);

        recyclerView = findViewById(R.id.listOwedbyme);
        new FirebaseDatabaseOwedByMe().readOwedByMe(new FirebaseDatabaseOwedByMe.DataStatus() {
            @Override
            public void DataIsLoaded(List<OwedByMeTransaction> OwedByMeTransaction, List<String> keys) {
                new OwedByMeRecyclerViewConfig().setConfig(recyclerView, ButtonOwedByMe.this, OwedByMeTransaction, keys);
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
        startActivity(new Intent(ButtonOwedByMe.this, Debthomepage.class));
        finish();
    }
}