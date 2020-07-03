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

public class FirebaseDataHelperExpense {
    private DatabaseReference databaseReference;
    private List<ExpenseTransaction> expenseTransactionList = new ArrayList<>();
    public String emailRemove = MainActivity.email;

    public void changeEmail(){
        emailRemove = emailRemove.replace(".",",");
    }

    public interface DataStatus{
        void DataIsLoaded(List<ExpenseTransaction> ExpenseTransaction, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public FirebaseDataHelperExpense(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ExpenseTransaction");
    }

    public void readExpense(final DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseTransactionList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    ExpenseTransaction expenseTransaction = keyNode.getValue(ExpenseTransaction.class);
                    expenseTransactionList.add(expenseTransaction);
                    changeEmail();
                    if(!expenseTransaction.getEmail().equals(emailRemove)){
                        expenseTransactionList.remove(expenseTransaction);
                    }
                }
                dataStatus.DataIsLoaded(expenseTransactionList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
