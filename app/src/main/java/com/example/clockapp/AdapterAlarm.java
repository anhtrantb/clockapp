package com.example.clockapp;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class AdapterAlarm extends RecyclerView.Adapter<AdapterAlarm.ViewHolder> {
    List<Alarm> listItem;
    Context context;
    boolean isLongClick = false;
    private SparseBooleanArray mSelectedItemsIds= new SparseBooleanArray(); ;//list chứa những id được chọn
    interface ItemAlarmListener{
        void onItemAlarmClickListener(int position,boolean isSelectedState);
    }
    ItemAlarmListener listener;
    public AdapterAlarm(Context context,List<Alarm> listItem,ItemAlarmListener listener) {
        this.context = context;
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
        if(isLongClick){
            displaySelectedState(holder,position);
        }else{
            displayNormalState(holder);
        }
    }
    //trạng thái select
    public  void displaySelectedState(ViewHolder holder, int currentPos){
        holder.mImgSelectAlarm.setVisibility(View.VISIBLE);
        holder.mSw_setAlarm.setVisibility(View.GONE);
        if(mSelectedItemsIds.get(currentPos)){
            holder.mImgSelectAlarm.setBackgroundResource(R.drawable.ic_check);
            holder.layoutItemAlarm.setBackground(context.getDrawable(R.drawable.bg_round_blue_light));
        }else{
            holder.mImgSelectAlarm.setBackgroundResource(R.drawable.ic_check_none);
            holder.layoutItemAlarm.setBackground(context.getDrawable(R.drawable.bg_round_white));
        }
    }
    //trạng thái bình thường
    public  void displayNormalState(ViewHolder holder){
        this.isLongClick = false;
        holder.mImgSelectAlarm.setVisibility(View.GONE);
        holder.mSw_setAlarm.setVisibility(View.VISIBLE);
        holder.layoutItemAlarm.setBackground(context.getDrawable(R.drawable.bg_round_white));
    }
    //khi chạm item
    public void toggleItem(int position){
        if(mSelectedItemsIds.get(position)){
            mSelectedItemsIds.delete(position);//->false
        }else   //=false/null
            mSelectedItemsIds.put(position,true);
    }

    public SparseBooleanArray getSelectedItemsIds() {
        return mSelectedItemsIds;
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTx_Time,mTx_Date, mTvAlarmName;
        Switch mSw_setAlarm;
        ImageButton mImgSelectAlarm;
        RelativeLayout layoutItemAlarm;
        final float  alphaTextView = 0.35f;
        ItemAlarmListener mListener;
        public ViewHolder(@NonNull View itemView, ItemAlarmListener mListener) {
            super(itemView);
            mTx_Time = itemView.findViewById(R.id.tx_time);
            mTx_Date = itemView.findViewById(R.id.tx_date);
            mSw_setAlarm = itemView.findViewById(R.id.sw_setAlarm);
            mTvAlarmName= itemView.findViewById(R.id.name_alarm);
            mImgSelectAlarm = itemView.findViewById(R.id.img_select_alarm);
            layoutItemAlarm = itemView.findViewById(R.id.layout_item_alarm);
            setAlphaTextView(alphaTextView);
            this.mListener = mListener;
            //sự kiện click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isLongClick){
                        toggleItem(getAdapterPosition());
                        displaySelectedState(ViewHolder.this,getAdapterPosition());
                        mListener.onItemAlarmClickListener(getAdapterPosition(),true);
                    }else
                        mListener.onItemAlarmClickListener(getAdapterPosition(),false);
                }
            });
            //sự kiện longclick
            itemView.setOnLongClickListener(v -> {
                toggleItem(getAdapterPosition());
                displaySelectedState(ViewHolder.this,getAdapterPosition());
                if(!isLongClick)//tại lần thứ 2 thì không cần thay đổi lại toàn bộ giao diện
                    notifyDataSetChanged();
                isLongClick = true;
                return false;
            });
            //sự kiện trạng thái của switch
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
