package com.example.clockapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clockapp.Base.ItemViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class ActivitySetAlarm extends AppCompatActivity implements ItemViewClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterDayOfWeek.onClickDayListener {
TimePicker timePicker;
TextView mTvExit, mTvSave, mTvRingtone, mTvVibrate, mTvPause;
EditText mEdtTitle;
ImageButton mDatePicker;
Switch mSwitchRingtoneSound, mSwitchVibrate, mSwitchPause;
RecyclerView recycleDayOfWeek;
AdapterDayOfWeek adapterDayOfWeek;
List<ItemDay> listDay = new ArrayList<>();
RelativeLayout layoutRingtoneSound, layoutVibrateMode, layoutPauseMode;
Alarm alarm = new Alarm();
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
        recycleDayOfWeek = findViewById(R.id.recycle_day_of_week);
        mEdtTitle = findViewById(R.id.edt_title_alarm);
        //text hiển thị cài đặt
        mTvRingtone = findViewById(R.id.tv_ringtone);
        mTvVibrate = findViewById(R.id.tv_vibrate);
        mTvPause = findViewById(R.id.tv_pause);
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
                Intent intent = new Intent(this, ActivityRingtoneSound.class);
                intent.putExtra(StaticName.SOUND_TITLE,alarm.getSoundTitle()==null ? RingTone.getFirst(this): alarm.getSoundTitle());
                startActivityForResult(intent, StaticName.CODE_SET_RINGTONE);
                break;
            case R.id.layout_vibrate_mode:
                Intent intentVibrateActivity = new Intent(this, ActivityVibrateMode.class);
                //gửi đi tên của chế độ rung
                intentVibrateActivity.putExtra("isSwitchOn",mSwitchVibrate.isChecked());
                intentVibrateActivity.putExtra(StaticName.VIBRATE_MODE,alarm.getVibrateMode()==null ? (new VibratePattern().getFirst()): alarm.getVibrateMode());
                startActivityForResult(intentVibrateActivity, StaticName.CODE_SET_VIBRATE);
                break;
            case R.id.layout_pause_mode:
                jumpToActivity(this, ActivityPauseMode.class);break;
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
                    mTvRingtone.setText(alarm.getSoundTitle()==null ? RingTone.getFirst(this): alarm.getSoundTitle());
                }else mTvRingtone.setText("Tắt");
            }
            case R.id.sw_vibrate:{
                if(mSwitchVibrate.isChecked()){
                    mTvVibrate.setText(alarm.getVibrateMode()==null ? new VibratePattern().getFirst(): alarm.getVibrateMode());
                }else mTvVibrate.setText("Tắt");
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
        if(requestCode == StaticName.CODE_SET_RINGTONE){
            if(resultCode== Activity.RESULT_OK){
                //title
                String titleSong = data.getStringExtra(StaticName.SOUND_TITLE);
                mTvRingtone.setText(titleSong);
                alarm.setSoundTitle(titleSong);
                alarm.setSoundUri(data.getStringExtra(StaticName.SOUND_URI));
                alarm.setHasReadLoudTime(data.getBooleanExtra(StaticName.HAS_READ_LOUD,true));
            }
        }
        else if(requestCode==StaticName.CODE_SET_VIBRATE){
            if(resultCode == Activity.RESULT_OK && data!=null){
                String vibrate_mode_rececived = data.getStringExtra(StaticName.VIBRATE_MODE);
                mSwitchVibrate.setChecked(data.getBooleanExtra("switchVibrateStatus",false));
                mTvVibrate.setText(vibrate_mode_rececived);
                alarm.setVibrateMode(vibrate_mode_rececived);
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveAlarm(){
        //giờ phút
        time.setHour(timePicker.getHour());
        time.setMinute(timePicker.getMinute());
        //tên báo thức
        alarm.setName(mEdtTitle.getText().toString());
        alarm.setTime(time);
        //các chế độ cài đặt
        alarm.setHasSound(mSwitchRingtoneSound.isChecked());
        alarm.setHasVibrate(mSwitchVibrate.isChecked());
        alarm.setHasPause(mSwitchPause.isChecked());
        //ngày trong tuần
        alarm.setListDaySet(getListDayIsChecked());
        //gửi kết quả về màn chính
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("data",alarm);
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void displayDatePicker(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    time.setDay(dayOfMonth);
                    time.setMonth(month);
                    time.setYear(year);
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
}