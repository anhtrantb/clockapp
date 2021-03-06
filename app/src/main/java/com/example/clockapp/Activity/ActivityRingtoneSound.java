package com.example.clockapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.clockapp.Adapter.AdapterSelectItem;
import com.example.clockapp.Object.ItemSelect;
import com.example.clockapp.R;
import com.example.clockapp.Utils.RingTone;
import com.example.clockapp.Object.SoundMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActivityRingtoneSound extends AppCompatActivity implements AdapterSelectItem.OnItemRadioCheck {
    Toolbar toolbar;
    RecyclerView recycleRingtone;
    Switch swReadLoudTime;
    List<ItemSelect> listRingtone = new ArrayList<>();
    AdapterSelectItem  adapterSelectItem;
    MediaPlayer mediaPlayer= new MediaPlayer();
    SoundMode soundMode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone_sound);
        initView();
        //lấy dữ liệu
        soundMode= (SoundMode) getIntent().getSerializableExtra("sound_mode");
        //thiết lập cho tool bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.ringtone_choosen));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        //recycle ringtone
        getListRingtone();
        adapterSelectItem = new AdapterSelectItem(listRingtone,this,getDefautSelectPosition());
        recycleRingtone.setAdapter(adapterSelectItem);
        recycleRingtone.setLayoutManager(new LinearLayoutManager(this));
    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        recycleRingtone = findViewById(R.id.recycle_ringtone);
        swReadLoudTime = findViewById(R.id.sw_read_loud_time);
    }

    public void getListRingtone(){
        /*hàm lấy toàn bộ chuông báo của máy
        sắp xếp theo tên nhạc chuông
        lấy tên và uri của bài hát*/
        Map<String, String> map = RingTone.getRingTone(this);
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            listRingtone.add(new ItemSelect(key, map.get(key)));
        }
        //sắp xếp theo chiều bảng chữ cái
        Comparator<ItemSelect> comp = new Comparator<ItemSelect>() {
            @Override
            public int compare(ItemSelect o1, ItemSelect o2) {
                return o1.getContent().compareTo(o2.getContent());
            }
        };
        Collections.sort(listRingtone,comp);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                //click vào nút back trên toolbar
                sendData();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemRadioChecked(int position) {
            mediaPlayer.stop();
            //phát nhạc khi click item
            mediaPlayer.reset();
            try {
                Uri uri = Uri.parse(listRingtone.get(position).getUri());
                mediaPlayer.setDataSource(this,uri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onBackPressed() {
        //nhấn nút back cứng
        sendData();
        super.onBackPressed();
    }
    public void sendData(){
        //gửi kết quả đã thiết lập về màn hình trước
        Intent intent = new Intent();
        soundMode.setSoundTitle(listRingtone.get(getPositionChecked()).getContent());
        soundMode.setSoundUri(listRingtone.get(getPositionChecked()).getUri());
        soundMode.setHasReadLoudTime(swReadLoudTime.isChecked());
        intent.putExtra("sound_mode",soundMode);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //dừng phát nhạc
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        mediaPlayer.release();
    }
    public int getDefautSelectPosition(){
        //nhận về vị trí mặc định gửi từ activity trước
        //lấy vị trí theo tên bài hát nhận được
        String titleRececive = soundMode.getSoundTitle();
        for(int i=0;i< listRingtone.size();i++){
            if(listRingtone.get(i).getContent().trim().equals(titleRececive)){
                return i;
            }
        }
        return -1;
    }
    public int getPositionChecked(){
        for(int i=0;i<listRingtone.size();i++){
            if(listRingtone.get(i).isChecked())
                return i;
        }
        return -1;
    }
}