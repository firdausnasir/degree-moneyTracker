package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IncomeRecyclerViewConfig {
    private Context mContext;
    private IncomeAdapter mIncomeAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<IncomeTransaction> incomeTransactions, List<String> keys){
        mContext = context;
        mIncomeAdapter = new IncomeAdapter(incomeTransactions, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mIncomeAdapter);
    }

    class IncomeItemView extends RecyclerView.ViewHolder{
        private TextView email, category, date, note, amount;

        private String key;

        public IncomeItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.income_list, parent, false));

            email = itemView.findViewById(R.id.textEmailIncome);
            category = itemView.findViewById(R.id.textCategoryIncome);
            date = itemView.findViewById(R.id.textDateIncome);
            note = itemView.findViewById(R.id.textNoteIncome);
            amount = itemView.findViewById(R.id.textAmountIncome);
        }

        public void bind(IncomeTransaction incomeTransaction, String key){
            email.setText("Email: " + incomeTransaction.getEmail());
            category.setText("Category: " + incomeTransaction.getCategory());
            date.setText("Date: " + incomeTransaction.getCurrDate());
            note.setText("Note: " + incomeTransaction.getNote());
            amount.setText(incomeTransaction.getAmount());

            this.key = key;
        }
    }

    class IncomeAdapter extends RecyclerView.Adapter<IncomeItemView>{
        private List<IncomeTransaction> mIncomeTransactions;
        private List<String> mKeys;

        public IncomeAdapter (List<IncomeTransaction> e, List<String> k){
            this.mIncomeTransactions = e;
            this.mKeys = k;
        }

        @NonNull
        @Override
        public IncomeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new IncomeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull IncomeItemView holder, int position) {
            holder.bind(mIncomeTransactions.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mIncomeTransactions.size();
        }
    }
}
