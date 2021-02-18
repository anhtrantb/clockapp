package com.example.clockapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ActivityVibrateMode extends AppCompatActivity implements AdapterSelectItem.OnItemRadioCheck, CompoundButton.OnCheckedChangeListener {
RecyclerView recycleVibrate;
AdapterSelectItem adapterVibrate;
List<ItemSelect> listVibrateMode = new ArrayList<>();
LinearLayout layoutRecycleVibrate;
RelativeLayout layoutSwitch;
Switch swSetVibrate;
Toolbar toolbar;
int positionChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate_mode);
        initView();
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        initRecycleVibrate(getIntent().getStringExtra(StaticName.VIBRATE_MODE));
        adapterVibrate = new AdapterSelectItem(listVibrateMode,this,positionChecked);
        recycleVibrate.setAdapter(adapterVibrate);
        recycleVibrate.setLayoutManager(new LinearLayoutManager(this));
        swSetVibrate.setOnCheckedChangeListener(this);
        swSetVibrate.setChecked(getIntent().getBooleanExtra("isSwitchOn",false));
        displayRecycleViewWithSwitch();
    }
    public void initView(){
        recycleVibrate = findViewById(R.id.recycle_vibrate);
        toolbar = findViewById(R.id.toolbar);
        swSetVibrate = findViewById(R.id.sw_set_vibrate);
        layoutRecycleVibrate = findViewById(R.id.layout_recycle_vibrate);
        layoutSwitch = findViewById(R.id.layout_switch);
    }

    @Override
    public void onItemRadioChecked(int position) {
        positionChecked = position;
    }
    public void initRecycleVibrate(String defaulPattern){
        String[] listNameVibrateMode= getResources().getStringArray(R.array.list_name_vibrate_mode);
        for(int i=0;i<listNameVibrateMode.length;i++){
            listVibrateMode.add(new ItemSelect(listNameVibrateMode[i]));
            if(listNameVibrateMode[i].trim().equals(defaulPattern)) {
                positionChecked = i;
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            saveSelectVibrate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveSelectVibrate();
        super.onBackPressed();
    }
    public void saveSelectVibrate(){
        Intent intent = new Intent();
        String valueSend  = getResources().getStringArray(R.array.list_name_vibrate_mode)[positionChecked];
        intent.putExtra(StaticName.VIBRATE_MODE,valueSend);
        intent.putExtra("switchVibrateStatus",swSetVibrate.isChecked());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        displayRecycleViewWithSwitch();
    }
    public void displayRecycleViewWithSwitch(){
        if(swSetVibrate.isChecked()){
            adapterVibrate.setItemClickable(true);
            layoutRecycleVibrate.setAlpha(1f);
            layoutSwitch.setBackground(getResources().getDrawable(R.drawable.bg_round_color_blue));
        }else{
            adapterVibrate.setItemClickable(false);
            layoutRecycleVibrate.setAlpha(0.3f);
            layoutSwitch.setBackground(getResources().getDrawable(R.drawable.bg_round_corner));
        }
    }
}