package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OwedByMeRecyclerViewConfig {

    private Context mContext;
    private OwedByMeRecyclerViewConfig.OwedByMeAdapter mOwedByMeAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<OwedByMeTransaction> owedbymeTransactions, List<String> keys){
        mContext = context;
        mOwedByMeAdapter = new OwedByMeRecyclerViewConfig.OwedByMeAdapter(owedbymeTransactions, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mOwedByMeAdapter);
    }

    class OwedByMeItemView extends RecyclerView.ViewHolder{
        private TextView email, name, amount;

        private String key;

        public OwedByMeItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.owedbyme_list, parent, false));

            email = itemView.findViewById(R.id.textEmail);
            name = itemView.findViewById(R.id.textName);
            amount = itemView.findViewById(R.id.textAmount);
        }

        public void bind(OwedByMeTransaction owedbymeTransaction, String key){
            email.setText("Email: " + owedbymeTransaction.getEmail());
            name.setText("Name: " + owedbymeTransaction.getName());
            amount.setText(String.valueOf(owedbymeTransaction.getAmount()));

            this.key = key;
        }
    }

    class OwedByMeAdapter extends RecyclerView.Adapter<OwedByMeRecyclerViewConfig.OwedByMeItemView>{
        private List<OwedByMeTransaction> mOwedByMeTransactions;
        private List<String> mKeys;

        public OwedByMeAdapter (List<OwedByMeTransaction> e, List<String> k){
            this.mOwedByMeTransactions = e;
            this.mKeys = k;
        }

        @NonNull
        @Override
        public OwedByMeRecyclerViewConfig.OwedByMeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OwedByMeRecyclerViewConfig.OwedByMeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull OwedByMeRecyclerViewConfig.OwedByMeItemView holder, int position) {
            holder.bind(mOwedByMeTransactions.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mOwedByMeTransactions.size();
        }
    }
}
