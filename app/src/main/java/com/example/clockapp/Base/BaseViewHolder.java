package com.example.clockapp.Base;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public ItemViewClickListener itemViewClickListener;
    public List<?> mData;

    public BaseViewHolder(@NonNull View itemView, ItemViewClickListener itemViewClickListener) {
        super(itemView);
        //set sự kiện click
        this.itemViewClickListener = itemViewClickListener;
    }
    //dùng cho findviewbyId
    public abstract void initViewHolder(View v);
    //dùng cho thiết lập liện quan
    public abstract void bindViewHolder(List<?> obj, int pos, Context context);
    //sự kiện click trên mỗi item
    public abstract void onItemViewClick(View v, int pos);

}
