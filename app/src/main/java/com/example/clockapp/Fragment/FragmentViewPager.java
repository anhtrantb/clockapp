package com.example.clockapp.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.clockapp.Adapter.AdapterFragmentViewPager;
import com.example.clockapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
//fragment hiển thị giao diện chính cho toàn bộ ứng dụng
public class FragmentViewPager extends Fragment {
    ViewPager2 viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager = view.findViewById(R.id.pager);
        //thiết lập adapter cho viewpager
        AdapterFragmentViewPager adapterFragment = new AdapterFragmentViewPager(this);
        viewPager.setAdapter(adapterFragment);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        //custom tablayout
        //--->>phải thêm trước khi liên kết với viewpager
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());
                int tabChildsCount = vgTab.getChildCount();
                for (int i = 0; i < tabChildsCount; i++) {
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView) {
                        //hiển thị chữ đậm
                        ((TextView) tabViewChild).setTypeface(Typeface.DEFAULT_BOLD);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());
                int tabChildsCount = vgTab.getChildCount();
                for (int i = 0; i < tabChildsCount; i++) {
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView) {
                        ((TextView) tabViewChild).setTypeface(Typeface.DEFAULT);
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //liên kết tab layout với view pager
        new TabLayoutMediator(tabLayout, viewPager,
            (tab, position) -> {
                switch (position){
                    //đặt tên cho từng tab
                    case 0:tab.setText(getString(R.string.alarm));
                        tab.setIcon(R.drawable.ic_alarm); break;
                    case 1:tab.setText(getString(R.string.stop_watch));
                        tab.setIcon(R.drawable.ic_stopwatch);break;
                    case 2:tab.setText(getString(R.string.count_down));
                        tab.setIcon(R.drawable.ic_hourglass);break;
                    case 3:tab.setText(getString(R.string.utility));
                        tab.setIcon(R.drawable.ic_util);break;
                    default: return;
                }
            }
        ).attach();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        FragmentAlarm frag = (FragmentAlarm) getChildFragmentManager().getFragments().get(viewPager.getCurrentItem());
        frag.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}

