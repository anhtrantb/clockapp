package com.example.clockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycleView  extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder> {
    ArrayList<ItemAlarm> listItem;
    Context context;

    public AdapterRecycleView(Context context, ArrayList<ItemAlarm> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_set_time,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemAlarm itemAlarm = listItem.get(position);
        holder.mTx_Time.setText(itemAlarm.getTime());
        holder.mTx_Date.setText(itemAlarm.getDate());
        holder.mSw_setAlarm.setChecked(itemAlarm.isCheck());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTx_Time,mTx_Date;
        Switch mSw_setAlarm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTx_Time = itemView.findViewById(R.id.tx_time);
            mTx_Date = itemView.findViewById(R.id.tx_date);
            mSw_setAlarm = itemView.findViewById(R.id.sw_setAlarm);
        }
    }
}
