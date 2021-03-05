package com.example.clockapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.ArrayList;
import java.util.List;

public class ActivitySetAlarm extends AppCompatActivity implements  View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterDayOfWeek.onClickDayListener {
TimePicker timePicker;
TextView mTvExit, mTvSave, mTvRingtone, mTvVibrate, mTvPause,mTvDateChoice;
EditText mEdtTitle;
ImageButton mDatePicker;
Switch mSwitchRingtoneSound, mSwitchVibrate, mSwitchPause;
RecyclerView recycleDayOfWeek;
AdapterDayOfWeek adapterDayOfWeek;
List<ItemDay> listDay = new ArrayList<>();
RelativeLayout layoutRingtoneSound, layoutVibrateMode, layoutPauseMode;
Alarm alarm ;
boolean isChangeDate = false;
Time time = new Time();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        initView();

        timePicker.setIs24HourView(true);
        mTvExit.setOnClickListener(v->finish());
        mTvSave.setOnClickListener(this);
        mDatePicker.setOnClickListener(this);

        layoutRingtoneSound.setOnClickListener(this);
        layoutVibrateMode.setOnClickListener(this);
        layoutPauseMode.setOnClickListener(this);

        mSwitchRingtoneSound.setOnCheckedChangeListener(this);
        mSwitchVibrate.setOnCheckedChangeListener(this);
        mSwitchPause.setOnCheckedChangeListener(this);

        alarm = (Alarm) getIntent().getSerializableExtra("alarm");
        updateData();
        String day[] = getResources().getStringArray(R.array.day_of_week);
        for(int i=0;i<day.length;i++){
            listDay.add(new ItemDay(day[i]));
        }
        adapterDayOfWeek = new AdapterDayOfWeek(listDay,this);
        recycleDayOfWeek.setLayoutManager(new GridLayoutManager(this,7));
        recycleDayOfWeek.setAdapter(adapterDayOfWeek);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        recycleDayOfWeek = findViewById(R.id.recycle_day_of_week);
        mEdtTitle = findViewById(R.id.edt_title_alarm);
        //text hiển thị cài đặt
        mTvRingtone = findViewById(R.id.tv_ringtone);
        mTvVibrate = findViewById(R.id.tv_vibrate);
        mTvPause = findViewById(R.id.tv_pause);
        mTvDateChoice = findViewById(R.id.txt_time_choice);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_picker:{
                displayDatePicker();
                break;
            }
            case R.id.layout_ringtone_sound:
                Intent intentRingtoneActivity = new Intent(this, ActivityRingtoneSound.class);
                intentRingtoneActivity.putExtra("sound_mode",alarm.getSoundMode());
                startActivityForResult(intentRingtoneActivity, StaticName.CODE_SET_RINGTONE);
                break;
            case R.id.layout_vibrate_mode:
                Intent intentVibrateActivity = new Intent(this, ActivityVibrateMode.class);
                alarm.getVibrateMode().setTurnOn(mSwitchVibrate.isChecked());
                intentVibrateActivity.putExtra("vibrate_mode",alarm.getVibrateMode());
                startActivityForResult(intentVibrateActivity, StaticName.CODE_SET_VIBRATE);
                break;
            case R.id.layout_pause_mode:
                Intent intentPauseActivity = new Intent(this, ActivityPauseMode.class);
                alarm.getPauseMode().setTurnOn(mSwitchPause.isChecked());
                intentPauseActivity.putExtra("pause_mode",alarm.getPauseMode());
                startActivityForResult(intentPauseActivity,StaticName.CODE_SET_PAUSE);
                break;
            case R.id.tv_save:
                saveAlarm(); break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //xử lí sự kiện cho các switch
        switch (buttonView.getId()){
            case R.id.sw_ringtone_sound:{
                if(mSwitchRingtoneSound.isChecked()){
                    mTvRingtone.setText(alarm.getSoundMode().getSoundTitle());
                }else mTvRingtone.setText("Tắt");
            }
            case R.id.sw_vibrate:{
                if(mSwitchVibrate.isChecked()){
                    mTvVibrate.setText(alarm.getVibrateMode().getName());
                }else mTvVibrate.setText("Tắt");
            }
            case R.id.sw_pause:{
                if(mSwitchPause.isChecked()){
                    mTvPause.setText(alarm.getPauseMode().display());
                }else{
                    mTvPause.setText("Tắt");
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == StaticName.CODE_SET_RINGTONE){
            if(resultCode== Activity.RESULT_OK){
                //nhận về chế độ âm thanh
                SoundMode soundMode = (SoundMode) data.getSerializableExtra("sound_mode");
                alarm.setSoundMode(soundMode);
                mTvRingtone.setText(soundMode.getSoundTitle());
                mSwitchRingtoneSound.setChecked(alarm.getSoundMode().isTurnOn());
            }
        }
        else if(requestCode==StaticName.CODE_SET_VIBRATE){
            if(resultCode == Activity.RESULT_OK && data!=null){
                //nhận về chế độ rung
                VibrateMode vibrateMode = (VibrateMode) data.getSerializableExtra("vibrate_mode");
                alarm.setVibrateMode(vibrateMode);
                mTvVibrate.setText(alarm.getVibrateMode().getName());
                mSwitchVibrate.setChecked(alarm.getVibrateMode().isTurnOn());
            }
        }
        else if(requestCode == StaticName.CODE_SET_PAUSE){
            if(resultCode == Activity.RESULT_OK && data !=null){
                //nhận về chế độ dừng
                PauseMode pauseMode = (PauseMode) data.getSerializableExtra("pause_mode");
                alarm.setPauseMode(pauseMode);
                mSwitchPause.setChecked(data.getBooleanExtra("isSwitchPauseOn",false));
                mTvPause.setText(pauseMode.display());
            }
        }
    }

    @Override
    public void onClickDay(View view, int position) {
        if(listDay.get(position).isChecked){
            view.setBackgroundResource(0);
            listDay.get(position).setChecked(false);
        }else{
            view.setBackgroundResource(R.drawable.round_txt);
            listDay.get(position).setChecked(true);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveAlarm(){
        //giờ phút
        time.setHour(timePicker.getHour());
        time.setMinute(timePicker.getMinute());
        getNextDay();
        //tên báo thức
        alarm.setName(mEdtTitle.getText().toString());
        alarm.setTime(time);
        //các chế độ cài đặt
        alarm.getSoundMode().setTurnOn(mSwitchRingtoneSound.isChecked());
        alarm.getVibrateMode().setTurnOn(mSwitchVibrate.isChecked());
        alarm.getPauseMode().setTurnOn(mSwitchPause.isChecked());
        //ngày trong tuần
        alarm.setListDaySet(getListDayIsChecked());
        //gửi kết quả về màn chính
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("data",alarm);
        int indexEdit = getIntent().getIntExtra("position_need_edit",-1);
        if(indexEdit!=-1){
            intent.putExtra("position_need_edit",indexEdit);
        }
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
    public ArrayList<String> getListDayIsChecked(){
        ArrayList<String> listDayIsChecked = new ArrayList<>();
        for(ItemDay itemDay : listDay){
            if(itemDay.isChecked())
                listDayIsChecked.add(itemDay.getDay());
        }
        return listDayIsChecked;
    }
    //hiển thị date picker
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void displayDatePicker(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    time.setDay(dayOfMonth);
                    time.setMonth(month);
                    time.setYear(year);
                    isChangeDate = true;
                    mTvDateChoice.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
    public void updateData(){
        mTvRingtone.setText(alarm.getSoundMode().getSoundTitle());
        mTvVibrate.setText(alarm.getVibrateMode().getName());
        mTvPause.setText(alarm.getPauseMode().display());
        mSwitchRingtoneSound.setChecked(alarm.getSoundMode().isTurnOn());
        mSwitchVibrate.setChecked(alarm.getVibrateMode().isTurnOn());
        mSwitchPause.setChecked(alarm.getPauseMode().isTurnOn());
        mEdtTitle.setText(alarm.getName());
    }
    public int compareTime(int h1, int m1, int h2, int m2){
        if(h1>h2) return  1;
        if(h1==h2){
            if(m1>m2) return  1;
            if(m1==m2) return  0;
            else return -1;
        }else return -1;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void getNextDay(){
        Calendar calendar = Calendar.getInstance();
        if(compareTime(timePicker.getHour(),timePicker.getMinute(),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE))!=1){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        if(!isChangeDate) {
            time.setYear(calendar.get(Calendar.YEAR));
            time.setMonth(calendar.get(Calendar.MONTH));
            time.setDay(calendar.get(Calendar.DATE));
        }
        time.setDayOfWeek(getStringDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
    }
    public String getStringDayOfWeek(int i){
        switch (i){
            case Calendar.MONDAY: return "T.2";
            case Calendar.TUESDAY: return "T.3";
            case Calendar.WEDNESDAY: return "T.4";
            case Calendar.THURSDAY: return "T.5";
            case Calendar.FRIDAY: return "T.6";
            case Calendar.SATURDAY: return "T.7";
            default: return "CN";
        }
    }

}