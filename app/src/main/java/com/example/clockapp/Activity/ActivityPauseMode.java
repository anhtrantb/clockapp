package com.example.clockapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.clockapp.Object.PauseMode;
import com.example.clockapp.R;

public class ActivityPauseMode extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Toolbar toolbar;
    Switch swSetPause;
    RelativeLayout layoutSwitch;
    RadioGroup groupTimePause, groupTimeRepeat;
    LinearLayout layoutSetPauseMode;
    TextView tvStatusSwitch;
    PauseMode pauseMode ;

    String[] listTimeRepeat ;
    String[] listTimePause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_mode);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        swSetPause.setOnCheckedChangeListener(this);

        if(getIntent().getBooleanExtra("isSwitchPauseOn",false)){
            swSetPause.setChecked(true);
        }else{
            swSetPause.setChecked(false);
        };
        displaySwitch();
        initRadioGroup();
        updateRadioGroup();

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        swSetPause = findViewById(R.id.sw_set_pause);
        layoutSwitch = findViewById(R.id.layout_switch);
        groupTimePause = findViewById(R.id.time_pause);
        groupTimeRepeat = findViewById(R.id.time_repeat);
        layoutSetPauseMode = findViewById(R.id.layout_set_pause_mode);
        tvStatusSwitch = findViewById(R.id.tv_status_switch);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        displaySwitch();
    }
    public void displaySwitch(){
        if(swSetPause.isChecked()){
            tvStatusSwitch.setText("Bật");
            layoutSetPauseMode.setAlpha(1f);
            layoutSetPauseMode.setEnabled(true);
            layoutSwitch.setBackground(getResources().getDrawable(R.drawable.bg_round_color_blue));
        }else{
            tvStatusSwitch.setText("Tắt");
            layoutSetPauseMode.setAlpha(0.3f);
            layoutSetPauseMode.setEnabled(false);
            layoutSwitch.setBackground(getResources().getDrawable(R.drawable.bg_round_corner));
        }
    }

    @Override
    public void onBackPressed() {
        saveValue();
        super.onBackPressed();
    }
    public void initRadioGroup(){
        listTimeRepeat = getResources().getStringArray(R.array.list_time_repeat);
        listTimePause = getResources().getStringArray(R.array.list_time_pause);
        for(int i=0;i< listTimePause.length;i++){
            RadioButton radioButton1 = new RadioButton(this);
            radioButton1.setText(listTimePause[i]);
            radioButton1.setTextSize(17);
            radioButton1.setPadding(0,20,0,20);
            groupTimePause.addView(radioButton1);
        }
        for(int i=0;i<listTimeRepeat.length;i++){
            RadioButton radioButton2 = new RadioButton(this);
            radioButton2.setText(listTimeRepeat[i]);
            radioButton2.setTextSize(17);
            radioButton2.setPadding(0,20,0,20);
            groupTimeRepeat.addView(radioButton2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            saveValue();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveValue(){
        RadioButton radioPause = findViewById(groupTimePause.getCheckedRadioButtonId());
        RadioButton radioRepeat = findViewById(groupTimeRepeat.getCheckedRadioButtonId());
        int timePause = getIndex(listTimePause,radioPause.getText().toString());
        int timeRepeat = getIndex(listTimeRepeat,radioRepeat.getText().toString());
        Intent intent = new Intent();
        pauseMode.setTimeName(radioPause.getText().toString());
        pauseMode.setRepeatName(radioRepeat.getText().toString());
        pauseMode.setTimeValue(getValuePause(timePause));
        pauseMode.setRepeatValue(getValueRepeat(timeRepeat));
        intent.putExtra("pause_mode",pauseMode);
        intent.putExtra("isSwitchPauseOn",swSetPause.isChecked());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
    public int getValuePause(int index){
        switch (index){
            case 0: return 5;
            case 1: return 10;
            case 2: return 15;
            case 3 : return 30;
            default: return -1;
        }
    }
    public int getValueRepeat(int index){
        switch (index){
            case 0: return 3;
            case 1: return 5;
            case 2: return 0;//mãi mãi
            default: return  -1;
        }
    }
    public int getIndex(String[] arr, String value){
        for(int i=0;i<arr.length;i++){
            if(arr[i].trim().equals(value))
                return i;
        }
        return -1;
    }
    public void updateRadioGroup(){
        if(getIntent().getSerializableExtra("pause_mode")==null){
            pauseMode = new PauseMode();
            groupTimeRepeat.check(groupTimeRepeat.getChildAt(0).getId());
            groupTimePause.check(groupTimePause.getChildAt(0).getId());
        }else {
            pauseMode = (PauseMode) getIntent().getSerializableExtra("pause_mode");
            groupTimeRepeat.check(groupTimeRepeat
                .getChildAt(getIndex(listTimeRepeat, pauseMode.getRepeatName()))
                .getId());
            groupTimePause.check(groupTimePause
                .getChildAt(getIndex(listTimePause,pauseMode.getTimeName()))
                .getId());
        }
    }
}