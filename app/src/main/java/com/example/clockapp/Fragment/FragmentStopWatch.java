package com.example.clockapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clockapp.Adapter.AdapterStopTime;
import com.example.clockapp.Object.ItemStopTime;
import com.example.clockapp.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentStopWatch extends Fragment implements View.OnClickListener {
Button btnStart, btnRestartClick, btnPauseResume;
TextView tvCountTime,tvSubStopTime;
LinearLayout layoutCountTime;
Handler handler = new Handler(Looper.getMainLooper());
final private long delayMillis = 100l;

//dùng cho danh sách giờ đã bấm
AdapterStopTime adapterStopTime;
RecyclerView recycleStopTime;
List<ItemStopTime> stopTimeList = new ArrayList<>();
//thời gian đếm chính
long MillisecondTime, startTime;
int seconds, minutes, milliSeconds;
//thời gian đếm phụ
int subSeconds, subMinutes, subMilliseconds;
long subStartTime, subMillisecondsTime;
boolean isStopWatch = false;//trạng thái đang bấm giờ
    private static FragmentStopWatch fragmentStopWatch;
    public static FragmentStopWatch getInstance(){
        if(fragmentStopWatch==null){
            fragmentStopWatch = new FragmentStopWatch();
        }
        return fragmentStopWatch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stop_watch, container, false);
        initView(view);
        btnStart.setOnClickListener(this);
        btnRestartClick.setOnClickListener(this);
        btnPauseResume.setOnClickListener(this);

        adapterStopTime = new AdapterStopTime(stopTimeList);
        recycleStopTime.setAdapter(adapterStopTime);
        recycleStopTime.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    public void initView(View view){
        btnStart = view.findViewById(R.id.btn_start);
        btnRestartClick = view.findViewById(R.id.btn_stop);
        btnPauseResume = view.findViewById(R.id.btn_pause_resume);
        tvCountTime = view.findViewById(R.id.tv_count_time);
        layoutCountTime = view.findViewById(R.id.layout_count_time);
        tvSubStopTime = view.findViewById(R.id.tv_sub_stoptime);
        recycleStopTime = view.findViewById(R.id.recycle_stop_time);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, delayMillis);
                isStopWatch = true;
                //animation
                displayButton();
                break;
            case R.id.btn_stop:
                if(isStopWatch){
                    //thêm những thời gian bấm được vào recycle view
                    subStartTime = SystemClock.uptimeMillis();
                    layoutCountTime.setVisibility(View.VISIBLE);
                    tvSubStopTime.setVisibility(View.VISIBLE);
                    if(stopTimeList.isEmpty()){
                        stopTimeList.add(new ItemStopTime(stopTimeList.size()+1,
                                String.format("%02d", minutes)+ ":"
                                        + String.format("%02d", seconds) + "."
                                        + String.format("%02d", milliSeconds),
                                String.format("%02d", minutes)+ ":"
                                        + String.format("%02d", seconds) + "."
                                        + String.format("%02d", milliSeconds)));
                    }else {
                        stopTimeList.add(new ItemStopTime(stopTimeList.size() + 1, String.format("%02d", subMinutes) + ":"
                                + String.format("%02d", subSeconds) + "."
                                + String.format("%02d", subMilliseconds),
                                String.format("%02d", minutes) + ":"
                                        + String.format("%02d", seconds) + "."
                                        + String.format("%02d", milliSeconds)));
                    }
                    adapterStopTime.notifyDataSetChanged();
                }else{
                    resetTime();
                }

                break;
            case R.id.btn_pause_resume:
                if(isStopWatch) {
                    handler.removeCallbacks(runnable);
                    isStopWatch = false;
                    btnPauseResume.setText(getString(R.string.tiep_tuc));
                    btnRestartClick.setText(getString(R.string.dat_lai));
                }
                else{
                    startTime = SystemClock.uptimeMillis()- MillisecondTime;
                    subStartTime = SystemClock.uptimeMillis() - subMillisecondsTime;
                    handler.postDelayed(runnable, delayMillis);
                    isStopWatch = true;
                    btnPauseResume.setText(getString(R.string.dung));
                    btnRestartClick.setText(getString(R.string.bam));
                }
                break;
        }
    }
    public Runnable runnable = new Runnable() {

        public void run() {
            //thời gian tổng
            MillisecondTime = SystemClock.uptimeMillis() - startTime;
            seconds = (int) (MillisecondTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (MillisecondTime % 1000)/10;//hiển thị 2 chữ số
            tvCountTime.setText(String.format("%02d", minutes)+ ":"
                    + String.format("%02d", seconds) + "."
                    + String.format("%02d", milliSeconds));
            //thời gian phụ
            subMillisecondsTime = SystemClock.uptimeMillis() - subStartTime;
            subSeconds = (int) (subMillisecondsTime / 1000);
            subMinutes = subSeconds / 60;
            subSeconds = subSeconds % 60;
            subMilliseconds = (int) (subMillisecondsTime % 1000)/10;//hiển thị 2 chữ số
            tvSubStopTime.setText(String.format("%02d", subMinutes)+ ":"
                    + String.format("%02d", subSeconds) + "."
                    + String.format("%02d", subMilliseconds));
            handler.postDelayed(this, delayMillis);
            }
        };
    //hàm hiển thị button , và set animation cho chúng 
    public void displayButton(){
        btnStart.setVisibility(View.INVISIBLE);
        Animation animCenterLeft = AnimationUtils.loadAnimation(getContext(),R.anim.center_to_left);
        Animation animCenterRight = AnimationUtils.loadAnimation(getContext(),R.anim.center_to_right);
        btnRestartClick.setVisibility(View.VISIBLE);
        btnPauseResume.setVisibility(View.VISIBLE);
        btnRestartClick.startAnimation(animCenterRight);
        btnPauseResume.startAnimation(animCenterLeft);
        //hiển thị text trên 2 button
        btnPauseResume.setText(getString(R.string.dung));
        btnRestartClick.setText(getString(R.string.bam));
    }
    public void hideButton(){
        Animation animLeftCenter = AnimationUtils.loadAnimation(getContext(),R.anim.left_to_center);
        Animation animRightCenter = AnimationUtils.loadAnimation(getContext(),R.anim.right_to_center);
        btnRestartClick.startAnimation(animRightCenter);
        btnPauseResume.startAnimation(animLeftCenter);
        btnRestartClick.setVisibility(View.INVISIBLE);
        btnPauseResume.setVisibility(View.INVISIBLE);
        tvSubStopTime.setVisibility(View.INVISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                btnStart.setVisibility(View.VISIBLE);
            }
        },500);
        layoutCountTime.setVisibility(View.INVISIBLE);
    }
    public void resetTime(){
        handler.removeCallbacks(runnable);
        hideButton();
        stopTimeList.clear();
        isStopWatch = false;
        tvCountTime.setText("00:00.00");
    }

}