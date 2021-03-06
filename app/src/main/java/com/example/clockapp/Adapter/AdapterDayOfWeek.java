package com.example.clockapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockapp.Object.ItemDay;
import com.example.clockapp.R;

import java.util.List;

public class AdapterDayOfWeek  extends RecyclerView.Adapter<AdapterDayOfWeek.ViewHolder> {
    ViewHolder viewHolder;
    List<ItemDay> listDay;
    onClickDayListener mListener;

    public interface onClickDayListener{
        void onClickDay(View view, int position);
    }

    public AdapterDayOfWeek(List<ItemDay> listDay,onClickDayListener mlistener) {
        this.listDay = listDay;
        this.mListener = mlistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_of_week,parent,false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position==listDay.size()-1){
            holder.tvDayOfWeek.setTextColor(Color.parseColor("#FB5043"));
        }
            holder.tvDayOfWeek.setText(listDay.get(position).getDay());
    }

    @Override
    public int getItemCount() {
        return listDay.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
    TextView tvDayOfWeek;
    onClickDayListener listener;
        public ViewHolder(@NonNull View itemView,onClickDayListener listener) {
            super(itemView);
            this.listener = listener;
            tvDayOfWeek= itemView.findViewById(R.id.tv_day_of_week);
            tvDayOfWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickDay(v,getAdapterPosition());
                }
            });
        }
    }
}
