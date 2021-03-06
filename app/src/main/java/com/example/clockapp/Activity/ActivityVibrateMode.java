package com.example.clockapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.clockapp.Adapter.AdapterSelectItem;
import com.example.clockapp.Object.ItemSelect;
import com.example.clockapp.R;
import com.example.clockapp.Object.VibrateMode;
import com.example.clockapp.Utils.VibratePattern;

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
TextView tvStatusSwitch;
VibrateMode vibrateMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate_mode);
        initView();
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        //lấy dữ liệu
        vibrateMode = (VibrateMode) getIntent().getSerializableExtra("vibrate_mode");
        initRecycleVibrate(vibrateMode.getName());
        adapterVibrate = new AdapterSelectItem(listVibrateMode,this,getPositionChecked());
        recycleVibrate.setAdapter(adapterVibrate);
        recycleVibrate.setLayoutManager(new LinearLayoutManager(this));
        swSetVibrate.setOnCheckedChangeListener(this);
        swSetVibrate.setChecked(vibrateMode.isTurnOn());
        switchChanged();
    }
    public void initView(){
        recycleVibrate = findViewById(R.id.recycle_vibrate);
        toolbar = findViewById(R.id.toolbar);
        swSetVibrate = findViewById(R.id.sw_set_vibrate);
        layoutRecycleVibrate = findViewById(R.id.layout_recycle_vibrate);
        layoutSwitch = findViewById(R.id.layout_switch);
        tvStatusSwitch = findViewById(R.id.tv_status_switch);
    }

    @Override
    public void onItemRadioChecked(int position) {

    }
    public void initRecycleVibrate(String defaulPattern){
        String[] listNameVibrateMode= getResources().getStringArray(R.array.list_name_vibrate_mode);
        for(int i=0;i<listNameVibrateMode.length;i++){
            listVibrateMode.add(new ItemSelect(listNameVibrateMode[i]));
            if(listNameVibrateMode[i].trim().equals(defaulPattern)) {
                listVibrateMode.get(i).setChecked(true);
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
        String valueSend  = getResources().getStringArray(R.array.list_name_vibrate_mode)[getPositionChecked()];
        vibrateMode.setName(valueSend);
        vibrateMode.setTurnOn(swSetVibrate.isChecked());
        vibrateMode.setPattern(new VibratePattern().getList().get(valueSend));
        intent.putExtra("vibrate_mode",vibrateMode);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switchChanged();
    }
    public void switchChanged(){
        if(swSetVibrate.isChecked()){
            tvStatusSwitch.setText("Bật");
            adapterVibrate.setItemClickable(true);
            layoutRecycleVibrate.setAlpha(1f);
            layoutSwitch.setBackground(getResources().getDrawable(R.drawable.bg_round_color_blue));
        }else{
            tvStatusSwitch.setText("Tắt");
            adapterVibrate.setItemClickable(false);
            layoutRecycleVibrate.setAlpha(0.3f);
            layoutSwitch.setBackground(getResources().getDrawable(R.drawable.bg_round_corner));
        }
    }
    public int getPositionChecked(){
        for(int i=0;i<listVibrateMode.size();i++){
            if(listVibrateMode.get(i).isChecked())
                return i;
        }
        return -1;
    }
}