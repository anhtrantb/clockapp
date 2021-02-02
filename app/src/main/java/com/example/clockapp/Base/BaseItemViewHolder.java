package com.example.clockapp.Base;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BaseItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private RecyclerView.ViewHolder mViewHolder;
    //--> dùng cho onCreateViewHolder
    public BaseItemViewHolder(@NonNull View itemView, RecyclerView.ViewHolder viewHolder) {
        super(itemView);
        this.mViewHolder = viewHolder;
        //sự kiện click
        if (viewHolder instanceof BaseViewHolder)
            itemView.setOnClickListener(this);
    }
    //->dùng cho onBindViewHolder
    public void onBindViewHolderBase(Context context, RecyclerView.ViewHolder viewHolder, List<?> obj, int pos) {
        if (viewHolder instanceof BaseViewHolder) {
            ((BaseViewHolder) viewHolder).mData = obj;
            ((BaseViewHolder) viewHolder).initViewHolder(itemView);
            ((BaseViewHolder) viewHolder).bindViewHolder(obj, pos, context);
        }
    }

    @Override
    public void onClick(View v) {
        if (mViewHolder instanceof BaseViewHolder) {
            ((BaseViewHolder) mViewHolder).onItemViewClick(v, getAdapterPosition());
        }
    }
}