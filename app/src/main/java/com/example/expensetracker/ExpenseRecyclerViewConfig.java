package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseRecyclerViewConfig {
    private Context mContext;
    private ExpenseAdapter mExpenseAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<ExpenseTransaction> expenseTransactions, List<String> keys){
        mContext = context;
        mExpenseAdapter = new ExpenseAdapter(expenseTransactions, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mExpenseAdapter);
    }

    class ExpenseItemView extends RecyclerView.ViewHolder{
        private TextView email, category, date, note, amount;

        private String key;

        public ExpenseItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.expense_list, parent, false));

            email = itemView.findViewById(R.id.textEmail);
            category = itemView.findViewById(R.id.textCategory);
            date = itemView.findViewById(R.id.textDate);
            note = itemView.findViewById(R.id.textNote);
            amount = itemView.findViewById(R.id.textAmount);
        }

        public void bind(ExpenseTransaction expenseTransaction, String key){
            email.setText("Email: " + expenseTransaction.getEmail());
            category.setText("Category: " + expenseTransaction.getCategory());
            date.setText("Date: " + expenseTransaction.getCurrDate());
            note.setText("Note: " + expenseTransaction.getNote());
            amount.setText(expenseTransaction.getAmount());

            this.key = key;
        }
    }

    class ExpenseAdapter extends RecyclerView.Adapter<ExpenseItemView>{
        private List<ExpenseTransaction> mExpenseTransactions;
        private List<String> mKeys;

        public ExpenseAdapter(List<ExpenseTransaction> e, List<String> k){
            this.mExpenseTransactions = e;
            this.mKeys = k;
        }

        @NonNull
        @Override
        public ExpenseItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ExpenseItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpenseItemView holder, int position) {
            holder.bind(mExpenseTransactions.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mExpenseTransactions.size();
        }
    }
}
