package com.example.clockapp.WorldTime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemMoveCallback extends ItemTouchHelper.Callback {
    //interface cho việc lắng nghe thay đổi sự kiện
    ItemTouchListener listener;

    public ItemMoveCallback(ItemTouchListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //truyền vào cờ cho hướng kéo và hướng vuốt ngang
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlag = 0;//không cần vuốt
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //bắt sự kiện khi mà item di chuyển
        //trả về vị trí ban đầu và vị trí cuối cùng
        listener.onRowMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //sự kiện khi vuốt item sang trái hoặc phải
        listener.onRowSwipe(viewHolder.getAdapterPosition(),direction);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        //khi vị trí thay đổi
        listener.onRowSelect(viewHolder);
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //khi nhả tay ra, không còn kéo hay vuốt
        listener.onRowClear(viewHolder);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //kết nối swipe view hay không
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //kết nối giữ lâu để kéo hay không
        return false;
    }
}
