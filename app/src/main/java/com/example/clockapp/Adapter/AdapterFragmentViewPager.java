package com.example.clockapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.clockapp.Fragment.FragmentAlarm;
import com.example.clockapp.Fragment.FragmentCountDown;
import com.example.clockapp.Fragment.FragmentStopWatch;
import com.example.clockapp.WorldTime.FragmentWorldTime;

public class AdapterFragmentViewPager extends FragmentStateAdapter {
    public AdapterFragmentViewPager(@NonNull Fragment fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            //khởi tạo fragment cho mỗi trang
            case 0: return FragmentAlarm.getInstance();
            case 1: return FragmentWorldTime.getInstance();
            case 2: return FragmentStopWatch.getInstance();
            case 3: return FragmentCountDown.getInstance();
            default:return null;
        }
    }
    //số lượng các trang
    @Override
    public int getItemCount() {
        return 4;
    }
}

