package com.example.expensetracker;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseOwedByMe {
    private DatabaseReference databaseReference;
    private List<OwedByMeTransaction> owedbymeTransactionList = new ArrayList<>();
    public String emailRemove = MainActivity.email;

    public void changeEmail(){
        emailRemove = emailRemove.replace(".",",");
    }

    public interface DataStatus{
        void DataIsLoaded(List<OwedByMeTransaction> OwedByMeTransaction, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseOwedByMe() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("OwedByMeTransaction");
    }

    public void readOwedByMe(final DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                owedbymeTransactionList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    OwedByMeTransaction owedbymeTransaction = keyNode.getValue(OwedByMeTransaction.class);
                    owedbymeTransactionList.add(owedbymeTransaction);
                    changeEmail();
                    if(!owedbymeTransaction.getEmail().equals(emailRemove)){
                        owedbymeTransactionList.remove(owedbymeTransaction);
                    }
                }
                dataStatus.DataIsLoaded(owedbymeTransactionList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
