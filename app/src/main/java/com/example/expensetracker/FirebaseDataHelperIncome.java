package com.example.expensetracker;

import android.os.Build;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataHelperIncome {
    private DatabaseReference databaseReference;
    private List<IncomeTransaction> incomeTransactionList = new ArrayList<>();
    public String emailRemove = MainActivity.email;

    public void changeEmail(){
        emailRemove = emailRemove.replace(".",",");
    }

    public interface DataStatus{
        void DataIsLoaded(List<IncomeTransaction> IncomeTransaction, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public FirebaseDataHelperIncome(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("IncomeTransaction");
    }

    public void readIncome(final DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                incomeTransactionList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    IncomeTransaction incomeTransaction = keyNode.getValue(IncomeTransaction.class);
                    incomeTransactionList.add(incomeTransaction);
                    changeEmail();
                    if(!incomeTransaction.getEmail().equals(emailRemove)){
                        incomeTransactionList.remove(incomeTransaction);
                    }
                }
                dataStatus.DataIsLoaded(incomeTransactionList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
