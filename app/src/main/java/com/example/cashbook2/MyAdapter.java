package com.example.cashbook2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Cash> cashList;
    private OnItemClickListener onItemClickListener;
    private boolean deleteMode = false;

    public MyAdapter(List<Cash> cashList) {
        this.cashList = cashList;
    }

    public interface OnItemClickListener {
        void onItemClick(Cash cash);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cash_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cash cash = cashList.get(position);
        holder.name.setText(cash.getName());
        holder.date.setText(cash.getDate());
        holder.type.setText(cash.getType());
        holder.amount.setText(cash.getAmount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteMode && onItemClickListener != null) {
                    onItemClickListener.onItemClick(cash);
                } else {
                    Toast.makeText(view.getContext(), cash.getDescription(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cashList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, type, amount;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
            type = view.findViewById(R.id.type);
            amount = view.findViewById(R.id.amount);
        }
    }
}