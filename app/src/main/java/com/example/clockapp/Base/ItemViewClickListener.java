package com.example.clockapp.Base;

import java.util.List;
// interface cho sự kiện item click ở recycle view
public interface ItemViewClickListener {
    void onItemViewClickListener(int position, List<?> list);
}
