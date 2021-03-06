package com.example.clockapp.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clockapp.Activity.ActivitySetting;
import com.example.clockapp.Adapter.AdapterCountTime;
import com.example.clockapp.R;
import com.example.clockapp.Object.Time;
import com.example.clockapp.service.ServiceCountTime;

import java.util.ArrayList;
import java.util.List;

public class FragmentCountDown extends Fragment implements View.OnClickListener, Toolbar.OnMenuItemClickListener, AdapterCountTime.OnItemClickListener {
    ProgressBar progressBar;
    NumberPicker pickerHour, pickerMinutes, pickerSeconds;
    LinearLayout layoutPicker;
    ConstraintLayout layoutProgress;
    TextView tvCountDownTime;
    Button btnStart, btnStop, btnPauseResume;
    RecyclerView recyclerView;
    Toolbar toolbar;
    List<Time> listAvailableCount;
    AdapterCountTime adapterCountTime;
    boolean isCountDown = false;

    long timeLeft ;

    final String dataStoreName = "AlarmDatabase";
    final String KEY_SAVE = "totalTimeMillis";
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private static FragmentCountDown fragmentCountDown;

    public static FragmentCountDown getInstance() {
        if (fragmentCountDown == null) {
            fragmentCountDown = new FragmentCountDown();
        }
        return fragmentCountDown;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferences = context.getSharedPreferences(dataStoreName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count_down, container, false);
        initView(view);
        initNumberPicker();
        getDataSaved();
        isCountDown = false;
        progressBar.setMax(100);
        progressBar.setProgress(20);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPauseResume.setOnClickListener(this);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toolbar.getMenu().findItem(R.id.ic_delete).setVisible(false);
        toolbar.setOnMenuItemClickListener(this);
        //recycle
        listAvailableCount = new ArrayList<>();
        adapterCountTime = new AdapterCountTime(listAvailableCount,this);
        recyclerView.setAdapter(adapterCountTime);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    public void initView(View view) {
        progressBar = view.findViewById(R.id.progressBar);
        pickerHour = view.findViewById(R.id.picker_hour);
        pickerMinutes = view.findViewById(R.id.picker_minutes);
        pickerSeconds = view.findViewById(R.id.picker_seconds);
        layoutPicker = view.findViewById(R.id.layout_picker);
        layoutProgress = view.findViewById(R.id.layout_progress);
        tvCountDownTime = view.findViewById(R.id.tv_count_down_time);
        btnStart = view.findViewById(R.id.btn_start);
        btnStop = view.findViewById(R.id.btn_stop);
        btnPauseResume = view.findViewById(R.id.btn_pause_resume);
        toolbar = view.findViewById(R.id.toolbar2);
        recyclerView = view.findViewById(R.id.recycle_count_time_availble);
    }

    //thiết lập cho number pịcker, gía trị min max
    @SuppressLint("DefaultLocale")
    public void initNumberPicker() {
        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(99);
        pickerMinutes.setMinValue(0);
        pickerMinutes.setMaxValue(59);
        pickerSeconds.setMinValue(0);
        pickerSeconds.setMaxValue(59);
        //hiển thị 2 chữ số
        pickerHour.setFormatter(i -> String.format("%02d", i));
        pickerMinutes.setFormatter(i -> String.format("%02d", i));
        pickerSeconds.setFormatter(i -> String.format("%02d", i));
    }

    //hiển thị lúc đang đếm giờ
    public void countDownState() {
        btnStart.setVisibility(View.INVISIBLE);
        Animation animCenterLeft = AnimationUtils.loadAnimation(getContext(), R.anim.center_to_left);
        Animation animCenterRight = AnimationUtils.loadAnimation(getContext(), R.anim.center_to_right);
        btnStop.setVisibility(View.VISIBLE);
        btnPauseResume.setVisibility(View.VISIBLE);
        btnStop.startAnimation(animCenterRight);
        btnPauseResume.startAnimation(animCenterLeft);
        //hiển thị text trên 2 button
        btnPauseResume.setText(getString(R.string.tam_dung));
        layoutPicker.setVisibility(View.INVISIBLE);
        layoutProgress.setVisibility(View.VISIBLE);
    }

    //hiển thị khi không đếm giờ
    public void countDownStop() {
        Animation animLeftCenter = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_center);
        Animation animRightCenter = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_center);
        btnStop.startAnimation(animRightCenter);
        btnPauseResume.startAnimation(animLeftCenter);
        btnStop.setVisibility(View.INVISIBLE);
        btnPauseResume.setVisibility(View.INVISIBLE);

        layoutPicker.setVisibility(View.VISIBLE);
        layoutProgress.setVisibility(View.INVISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(() -> btnStart.setVisibility(View.VISIBLE), 500);
    }

    //sự kiện click các nút bấm trạng thái
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: //start count time
                if (getTotalTimeInMilliseconds() != 0) {
                    isCountDown = true;
                    countDownState();
                    //start service
                    startCountService(getTotalTimeInMilliseconds());
                } else {
                    Toast.makeText(getContext(), "vui lòng chọn thời gian", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_pause_resume:
                if (isCountDown) {
                    isCountDown = false;
                    stopCountService();
                    btnPauseResume.setText(getString(R.string.tiep_tuc));
                } else {
                    isCountDown = true;
                    startCountService(timeLeft);
                    btnPauseResume.setText(getString(R.string.tam_dung));
                }
                break;
            case R.id.btn_stop:
                countDownStop();
                stopCountService();
                break;
        }
    }

    //lấy tổng thời gian đếm ngược
    public long getTotalTimeInMilliseconds() {
        return (pickerHour.getValue() * 3600 + pickerMinutes.getValue() * 60 + pickerSeconds.getValue()) * 1000;
    }
//sự kiện cho từng icon trên thanh toolbar
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_add:
                displayDialog();
                break;
            case R.id.ic_setting:
                Intent intent = new Intent(getActivity(), ActivitySetting.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    //hiển thị dialog
    public void displayDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_count_time);
        dialog.getWindow()
                .setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.bg_round_white));
        TextView tvHour = dialog.findViewById(R.id.dialog_tv_hour);
        TextView tvMinutes = dialog.findViewById(R.id.dialog_tv_minutes);
        TextView tvSeconds = dialog.findViewById(R.id.dialog_tv_seconds);
        EditText edtTime = dialog.findViewById(R.id.dialog_name_time);
        tvHour.setSelectAllOnFocus(true);
        tvMinutes.setSelectAllOnFocus(true);
        tvSeconds.setSelectAllOnFocus(true);
        dialog.findViewById(R.id.tv_exit).setOnClickListener(v->dialog.dismiss());
        dialog.findViewById(R.id.tv_save).setOnClickListener(v->{
            if(getIntFromTextView(tvHour)!=0||getIntFromTextView(tvMinutes)!=0||getIntFromTextView(tvSeconds)!=0){
                listAvailableCount.add(new Time(edtTime.getText().toString(),
                        getIntFromTextView(tvHour),getIntFromTextView(tvMinutes),getIntFromTextView(tvSeconds)));
                adapterCountTime.notifyItemInserted(listAvailableCount.size()-1);
            }
            dialog.dismiss();
        });
        dialog.show();
    }
    //lấy giá trị nguyên từ một text view
    public int getIntFromTextView(TextView tv){
       try{
           return Integer.valueOf(tv.getText().toString());
       }catch (ClassCastException e){
           return 0;
       }
    }
//sự kiện click cho item đếm giờ được thiết lập sẵn
    @Override
    public void onItemClick(int position) {
        Time timePicker = listAvailableCount.get(position);
        pickerHour.setValue(timePicker.getHour());
        pickerMinutes.setValue(timePicker.getMinute());
        pickerSeconds.setValue(timePicker.getSecond());
    }
    //sự kiện long click cho item đếm giờ được thiết lập sẵn
    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Chú ý");
        builder.setMessage("Xóa item này?");
        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listAvailableCount.remove(position);
                adapterCountTime.notifyItemRemoved(position);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("không", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
    //start service
    public void startCountService(long timeCount){
        Intent intentService = new Intent(getActivity(), ServiceCountTime.class);
        intentService.putExtra("totalTime",timeCount);
        getActivity().startService(intentService);
    }
    public void stopCountService(){
        getActivity().stopService(new Intent(getActivity(),ServiceCountTime.class));
    }
    //nhận dữ liệu từ service trả về
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
            long millis = intent.getLongExtra("timeMilli",0);
            timeLeft = millis;
            if(millis == 0){
                progressBar.setProgress(0);
            }else{
                if(!isCountDown) {
                    countDownState();
                    isCountDown = true;
                    Log.i("running","aksdghjk");
                }
                tvCountDownTime.setText(Time.getTimeStringFromMilliseconds(millis));
                progressBar.setProgress((int) (millis * 100 / getTotalTimeInMilliseconds()));
            }
         }
    };
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver,
                new IntentFilter(ServiceCountTime.COUNTDOWN_BR));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
        saveData();
    }
    //lưu trạng thái
    public void saveData(){
        editor.putLong(KEY_SAVE,getTotalTimeInMilliseconds());
        editor.commit();
    }
    public void getDataSaved(){
        long timeMillisSaved = sharedPreferences.getLong(KEY_SAVE,0);
        int seconds = (int) (timeMillisSaved / 1000);//tổng số giây
        int hour = seconds / 3600;
        int minutes = seconds / 60 % 60;
        seconds = seconds % 60;
        pickerHour.setValue(hour);
        pickerMinutes.setValue(minutes);
        pickerSeconds.setValue(seconds);
    }
}