package com.example.clockapp.WorldTime;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockapp.R;

import java.util.ArrayList;
import java.util.Collections;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements ItemTouchListener {
    ArrayList<String> listTime;
    StartDragListener startDragListener;
    public Adapter(ArrayList<String> listTime,StartDragListener startDragListener) {
        this.listTime = listTime;
        this.startDragListener = startDragListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_world_time,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTime.setText(listTime.get(position));
        holder.imvMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    startDragListener.requestDrag(holder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTime.size();
    }

    @Override
    public void onRowMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listTime, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listTime, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSwipe(int position, int direction) {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onRowSelect(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof Adapter.ViewHolder){
            Adapter.ViewHolder holder = (ViewHolder) viewHolder;
            holder.layout.setBackgroundColor(Color.parseColor("#526df7"));
        }
    }

    @Override
    public void onRowClear(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof Adapter.ViewHolder){
            Adapter.ViewHolder holder = (ViewHolder) viewHolder;
            holder.layout.setBackgroundColor(Color.WHITE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTime;
        ImageView imvMove;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            imvMove = itemView.findViewById(R.id.imv_move);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
