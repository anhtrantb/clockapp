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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements ItemTouchListener {
    List<CountryUtil.CountryModel> list;
    StartDragListener startDragListener;
    public Adapter(List<CountryUtil.CountryModel> list,StartDragListener startDragListener) {
        this.list = list;
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
        holder.tvCountry.setText(list.get(position).getName());
        holder.tvCity.setText(list.get(position).getcity());
        holder.tvTime.setText(getTimeFromTimeZone(list.get(position).getTimeGmt()));
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
        return list.size();
    }

    @Override
    public void onRowMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
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
            holder.layout.setBackgroundColor(Color.parseColor("#0e5375"));
        }
    }

    @Override
    public void onRowClear(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof Adapter.ViewHolder){
            Adapter.ViewHolder holder = (ViewHolder) viewHolder;
            holder.layout.setBackgroundColor(Color.parseColor("#0e5375"));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCountry, tvCity, tvTime;
        ImageView imvMove;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tv_country);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvTime = itemView.findViewById(R.id.tv_time);
            imvMove = itemView.findViewById(R.id.imv_move);
            layout = itemView.findViewById(R.id.layout);
        }
    }
    public void updateList(List<CountryUtil.CountryModel> listToUpdate){
        list.clear();
        list.addAll(listToUpdate);
        notifyDataSetChanged();
    }
    private  String getTimeFromTimeZone(String gmt){
        java.util.TimeZone timeZone= java.util.TimeZone.getTimeZone(gmt);
        Calendar calendar = Calendar.getInstance(timeZone);
        return String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
                String.format("%02d", calendar.get(Calendar.MINUTE)) +" "+
                calendar.get(Calendar.DATE)+"/"+
                (calendar.get(Calendar.MONTH)+1);
    }
}
