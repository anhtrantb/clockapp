package com.example.clockapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clockapp.Base.ItemViewClickListener;

import java.util.List;

public class ActivitySetAlarm extends AppCompatActivity implements ItemViewClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
TimePicker timePicker;
TextView mTvExit, mTvSave;
ImageButton mDatePicker;
Switch mSwitchRingtoneSound, mSwitchVibrate, mSwitchPause;
private  int CODE_SET_RINGTONE = 2;
RelativeLayout layoutRingtoneSound, layoutVibrateMode, layoutPauseMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        initView();

        timePicker.setIs24HourView(true);
        mTvExit.setOnClickListener(v->finish());
        mDatePicker.setOnClickListener(this);

        layoutRingtoneSound.setOnClickListener(this);
        layoutVibrateMode.setOnClickListener(this);
        layoutPauseMode.setOnClickListener(this);

        mSwitchRingtoneSound.setOnCheckedChangeListener(this);
        mSwitchVibrate.setOnCheckedChangeListener(this);
        mSwitchPause.setOnCheckedChangeListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemViewClickListener(int position, List<?> list) {

    }
    public void initView(){
        timePicker = findViewById(R.id.time_picker);
        mTvExit = findViewById(R.id.tv_exit);
        mTvSave = findViewById(R.id.tv_save);
        mDatePicker = findViewById(R.id.date_picker);
        mSwitchRingtoneSound = findViewById(R.id.sw_ringtone_sound);
        mSwitchPause = findViewById(R.id.sw_pause);
        mSwitchVibrate = findViewById(R.id.sw_vibrate);
        layoutRingtoneSound = findViewById(R.id.layout_ringtone_sound);
        layoutPauseMode = findViewById(R.id.layout_pause_mode);
        layoutVibrateMode = findViewById(R.id.layout_vibrate_mode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_picker:{
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        }
                    },2021,2,2);
                datePickerDialog.show();
                break;
            }
            case R.id.layout_ringtone_sound:
                Intent intent = new Intent(this, ActivityRingtoneSound.class);
                intent.putExtra(StaticName.SOUND_TITLE,"Tam Sinh Tam Thế / 三生三世 (Tam Sinh Tam Thế: Thập Lý Đào Hoa Ost) - Jason Zhang (Trương Kiệt)");
                startActivityForResult(intent, CODE_SET_RINGTONE);
                break;
            case R.id.layout_vibrate_mode:
                jumpToActivity(this,ActivityVibrateMode.class);break;

            case R.id.layout_pause_mode:
                jumpToActivity(this, ActivityPauseMode.class);break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //xử lí sự kiện cho các switch
        switch (buttonView.getId()){
            case R.id.sw_ringtone_sound:{
            }
            case R.id.sw_vibrate:{

            }
            case R.id.sw_pause:{

            }
        }
    }
    public void jumpToActivity(Context context, Class cls){
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_SET_RINGTONE){
            if(resultCode== Activity.RESULT_OK){
                String y = data.getStringExtra(StaticName.SOUND_TITLE);
                String s = data.getStringExtra(StaticName.SOUND_URI);
                String x = String.valueOf(data.getBooleanExtra(StaticName.HAS_READ_LOUD,true));
                Log.e("tag",s+"  "+ x+" "+y);
            }
        }
    }
}