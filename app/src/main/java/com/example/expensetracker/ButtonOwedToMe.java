package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ButtonOwedToMe extends AppCompatActivity{

        private RecyclerView recyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttonowedtome);

        recyclerView = findViewById(R.id.listOwedtome);
        new FirebaseDatabaseOwedToMe().readOwedToMe(new FirebaseDatabaseOwedToMe.DataStatus() {
            @Override
            public void DataIsLoaded(List<OwedToMeTransaction> OwedToMeTransaction, List<String> keys) {
                new OwedToMeRecyclerViewConfig().setConfig(recyclerView, ButtonOwedToMe.this, OwedToMeTransaction, keys);
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
        startActivity(new Intent(ButtonOwedToMe.this, Debthomepage.class));
        finish();
    }
}
