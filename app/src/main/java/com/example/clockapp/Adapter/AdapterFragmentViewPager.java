package com.example.clockapp.Adapter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.clockapp.Fragment.FragmentAlarm;
import com.example.clockapp.Fragment.FragmentCountDay;
import com.example.clockapp.Fragment.FragmentCountDown;
import com.example.clockapp.Fragment.FragmentStopWatch;
import com.example.clockapp.Fragment.FragmentWorldTime;


public class AdapterFragmentViewPager extends FragmentStateAdapter {
    public AdapterFragmentViewPager(@NonNull Fragment fragment) {
        super(fragment);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            //khởi tạo fragment cho mỗi trang
            case 0: return FragmentAlarm.getInstance();
            case 1: return FragmentStopWatch.getInstance();
            case 2: return FragmentCountDown.getInstance();
            case 3: return FragmentWorldTime.getInstance();
            case 4: return FragmentCountDay.getInstance();
            default:return null;
        }
    }
    //số lượng các trang
    @Override
    public int getItemCount() {
        return 5;
    }
}

