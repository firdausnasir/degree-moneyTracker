package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OwedToMeRecyclerViewConfig {

    private Context mContext;
    private OwedToMeRecyclerViewConfig.OwedToMeAdapter mOwedToMeAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<OwedToMeTransaction> owedtomeTransactions, List<String> keys){
        mContext = context;
        mOwedToMeAdapter = new OwedToMeRecyclerViewConfig.OwedToMeAdapter(owedtomeTransactions, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mOwedToMeAdapter);
    }

    class OwedToMeItemView extends RecyclerView.ViewHolder{
        private TextView email, name, amount;

        private String key;

        public OwedToMeItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.owedtome_list, parent, false));

            email = itemView.findViewById(R.id.textEmail);
            name = itemView.findViewById(R.id.textName);
            amount = itemView.findViewById(R.id.textAmount);
        }

        public void bind(OwedToMeTransaction owedtomeTransaction, String key){
            email.setText("Email: " + owedtomeTransaction.getEmail());
            name.setText("Name: " + owedtomeTransaction.getName());
            amount.setText(String.valueOf(owedtomeTransaction.getAmount()));

            this.key = key;
        }
    }

    class OwedToMeAdapter extends RecyclerView.Adapter<OwedToMeRecyclerViewConfig.OwedToMeItemView>{
        private List<OwedToMeTransaction> mOwedToMeTransactions;
        private List<String> mKeys;

        public OwedToMeAdapter(List<OwedToMeTransaction> e, List<String> k){
            this.mOwedToMeTransactions = e;
            this.mKeys = k;
        }

        @NonNull
        @Override
        public OwedToMeRecyclerViewConfig.OwedToMeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OwedToMeRecyclerViewConfig.OwedToMeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull OwedToMeRecyclerViewConfig.OwedToMeItemView holder, int position) {
            holder.bind(mOwedToMeTransactions.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mOwedToMeTransactions.size();
        }
    }
}
