package com.example.expensetracker;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseOwedToMe {

    private DatabaseReference databaseReference;
    private List<OwedToMeTransaction> owedtomeTransactionList = new ArrayList<>();
    public String emailRemove = MainActivity.email;
    public void changeEmail(){
        emailRemove = emailRemove.replace(".",",");
    }

    public interface DataStatus{
        void DataIsLoaded(List<OwedToMeTransaction> OwedToMeTransaction, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseOwedToMe() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("OwedToMeTransaction");
    }

    public void readOwedToMe(final DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                owedtomeTransactionList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    OwedToMeTransaction owedtomeTransaction = keyNode.getValue(OwedToMeTransaction.class);
                    owedtomeTransactionList.add(owedtomeTransaction);
                    changeEmail();
                    if(!owedtomeTransaction.getEmail().equals(emailRemove)){
                        owedtomeTransactionList.remove(owedtomeTransaction);
                    }
                }
                dataStatus.DataIsLoaded(owedtomeTransactionList,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
