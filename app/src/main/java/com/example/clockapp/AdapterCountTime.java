package com.example.clockapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterCountTime extends RecyclerView.Adapter<AdapterCountTime.ViewHolder> {
    List<Time> listItem;
    OnItemClickListener listener;

    interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    public AdapterCountTime(List<Time> listItem, OnItemClickListener listener) {
        this.listItem = listItem;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_count, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvAvailableCountTime.setText(listItem.get(position).getFullTime());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvAvailableCountTime;
        OnItemClickListener mListener;
        ImageButton mImvCheck;
        public ViewHolder(@NonNull View itemView,OnItemClickListener mListener) {
            super(itemView);
            mImvCheck = itemView.findViewById(R.id.imv_check);
            tvAvailableCountTime = itemView.findViewById(R.id.tv_available_counttime);
            this.mListener = mListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition());
            return false;
        }
    }
}
