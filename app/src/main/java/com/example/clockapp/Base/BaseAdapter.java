package com.example.clockapp.Base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
    lớp base adapter là cơ sở cho mọi adapter khác nhau
    truyền vào một list, layout, context và 1 view holder
*/
public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<?> mList;
    private Context mContext;
    private int mLayout;
    private RecyclerView.ViewHolder viewHolder;

    private int mItemCount = 0; //số lượng item mặc định nếu không truyền giá trị vào

    public BaseAdapter(List<?> mList, Context mContext, int mLayout, RecyclerView.ViewHolder viewHolder) {
        this.mList = mList;
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.viewHolder = viewHolder;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        //-->default return new ViewHolder(view)
        //-->cần truyền vào ViewHolder và view cho lớp base xử lí
        return new BaseItemViewHolder(view, viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).onBindViewHolderBase(mContext, viewHolder, mList, position);
    }

    @Override
    public int getItemCount() {
        //nếu số lượng truyền vào lớn hơn, hoặc không được truyền vào
        if(mItemCount==0 || mItemCount>mList.size())
            return mList.size();
        else return mItemCount;
    }
    public void setList(List<?> list){
        this.mList = list;
        notifyDataSetChanged();
    }
    public void setItemCount(int size){
        this.mItemCount = size;
    }

}
