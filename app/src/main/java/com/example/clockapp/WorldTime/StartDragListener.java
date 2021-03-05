package com.example.clockapp.WorldTime;

import androidx.recyclerview.widget.RecyclerView;

public interface StartDragListener {
    //interface cho lắng nghe khi người dùng chạm vào icon gắn trên item
    //của view holder
    void requestDrag(RecyclerView.ViewHolder viewHolder);
}
