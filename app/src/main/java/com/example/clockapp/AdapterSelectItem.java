package com.example.clockapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSelectItem  extends RecyclerView.Adapter<AdapterSelectItem.ViewHolder> {
    private List<ItemSelect> listItem;
    //interface cho sự kiện click
    public interface OnRingtoneListener{
        void onRingtoneClick(int position);
    }
    private OnRingtoneListener mOnRingtoneListener;
    public AdapterSelectItem(List<ItemSelect> listItem, OnRingtoneListener mOnRingtoneListener) {
        this.listItem = listItem;
        this.mOnRingtoneListener = mOnRingtoneListener;
    }

    @NonNull
    @Override
    public AdapterSelectItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_content,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnRingtoneListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelectItem.ViewHolder holder, int position) {
       holder.tvContent.setText(listItem.get(position).getContent());
       if(listItem.get(position).isChecked()){
           holder.radioSelect.setChecked(true);
       }else{
           holder.radioSelect.setChecked(false);
       }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    RadioButton radioSelect;
    TextView tvContent;
        OnRingtoneListener onRingtoneListener;
        public ViewHolder(@NonNull View itemView,OnRingtoneListener mOnRingtoneListener ) {
            super(itemView);
            initViewHolder(itemView);
            this.onRingtoneListener = mOnRingtoneListener;
            itemView.setOnClickListener(this);
        }
        public void initViewHolder(View view){
            radioSelect = view.findViewById(R.id.radio_select);
            tvContent = view.findViewById(R.id.tv_content);
        }

        @Override
        public void onClick(View v) {
            onRingtoneListener.onRingtoneClick(getAdapterPosition());
        }
    }
}
