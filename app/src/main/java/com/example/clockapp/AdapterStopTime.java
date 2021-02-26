package com.example.clockapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterStopTime extends RecyclerView.Adapter<AdapterStopTime.ViewHolder> {
    List<ItemStopTime> listItem;

    public AdapterStopTime(List<ItemStopTime> listItem) {
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stop_watch,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemStopTime item = listItem.get(position);
        holder.index.setText(item.getIndex()+"");
        holder.timeRound.setText(item.getTimeRound());
        holder.timeAll.setText(item.getTimeAll());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
    TextView index, timeRound, timeAll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index);
            timeRound = itemView.findViewById(R.id.time_round);
            timeAll = itemView.findViewById(R.id.time_all);
        }
    }
}
