package com.example.clockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterAlarm extends RecyclerView.Adapter<AdapterAlarm.ViewHolder> {
    List<Alarm> listItem;
    interface ItemAlarmListener{
        void onItemAlarmClickListener(int position);
    }
    ItemAlarmListener listener;
    public AdapterAlarm(List<Alarm> listItem,ItemAlarmListener listener) {
        this.listItem = listItem;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set_time,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alarm alarm = listItem.get(position);
        holder.mTx_Time.setText(alarm.getTime().getHour()+":"+alarm.getTime().getMinute());
        holder.mTx_Date.setText(alarm.getTime().displayDate());
        holder.mSw_setAlarm.setChecked(alarm.isTurnOn());
        holder.mTvAlarmName.setText(alarm.getName());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTx_Time,mTx_Date, mTvAlarmName;
        Switch mSw_setAlarm;
        final float  alphaTextView = 0.35f;
        ItemAlarmListener mListener;
        public ViewHolder(@NonNull View itemView, ItemAlarmListener mListener) {
            super(itemView);
            mTx_Time = itemView.findViewById(R.id.tx_time);
            mTx_Date = itemView.findViewById(R.id.tx_date);
            mSw_setAlarm = itemView.findViewById(R.id.sw_setAlarm);
            mTvAlarmName= itemView.findViewById(R.id.name_alarm);
            setAlphaTextView(alphaTextView);
            this.mListener = mListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemAlarmClickListener(getAdapterPosition());
                }
            });
            mSw_setAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //set độ mờ cho text view
                    setAlphaTextView(alphaTextView);
                    listItem.get(getAdapterPosition()).setTurnOn(isChecked);
                }
            });
        }
        public void setAlphaTextView(float alpha){
            if(mSw_setAlarm.isChecked()){
                mTx_Time.setAlpha(1);
                mTx_Date.setAlpha(1);
            }else{
                mTx_Time.setAlpha(alpha);
                mTx_Date.setAlpha(alpha);
            }
        }
    }
}
