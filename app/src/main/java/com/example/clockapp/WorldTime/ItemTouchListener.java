package com.example.clockapp.WorldTime;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchListener {
    void onRowMove(int oldPosition, int newPosition);
    void onRowSwipe(int position, int direction);
    void onRowSelect(RecyclerView.ViewHolder viewHolder);
    void onRowClear(RecyclerView.ViewHolder viewHolder);
}
