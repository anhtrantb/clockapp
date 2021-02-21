package com.example.clockapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentAlarm extends Fragment implements AdapterAlarm.ItemAlarmListener {
    LinearLayout linearLayout;
    AppBarLayout appBarLayout;
    TextView title_collapsingbar_time,title_collapsingbar;
    RecyclerView recyclerViewAlarm;
    Toolbar mtoolbar;

    List<Alarm> listAlarm ;
    AdapterAlarm mAdapterAlarm;
    final String dataStoreName = "listAlarmData";
    final String keySave = "keySave";
    private  SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private static FragmentAlarm fragmentAlarm;
    public static FragmentAlarm getInstance(){
       if(fragmentAlarm==null){
           fragmentAlarm = new FragmentAlarm();
       }
       return fragmentAlarm;
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

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        //find view
        findView(view);
        //main
        title_collapsingbar.setText("Chuông báo sau 18 giờ 1 phút");
        title_collapsingbar_time.setText("06:00, T.6, 29 Th1");

        ((MainActivity) getActivity()).setSupportActionBar(mtoolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.alarm_ring));
        //làm mờ chữ khi collap
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            float percentage = ((float) Math.abs(verticalOffset) / appBarLayout1.getTotalScrollRange());
            linearLayout.setAlpha(1- 2 * percentage);
        });
        if(getDataSaved()!=null) listAlarm = getDataSaved();
        else listAlarm = new ArrayList<>();
        mAdapterAlarm = new AdapterAlarm(getContext(),listAlarm,this);
        recyclerViewAlarm.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAlarm.setAdapter(mAdapterAlarm);

        return view;
    }

        @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_toolbar,menu);
    }
//xử lí menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_add:{
                Intent intent = new Intent(getActivity(),ActivitySetAlarm.class);
                intent.putExtra("position_need_edit",-1);
                intent.putExtra("alarm",getDefaultAlarm());
                getParentFragment().startActivityForResult(intent,StaticName.CODE_EDIT_ALARM);
            }
            case R.id.ic_menu:{
                //Toast.makeText(getContext(), "world", Toast.LENGTH_SHORT).show(); break;
            }
            case R.id.ic_delete:{

            }
            case R.id.ic_setting:{

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void findView(View view){
        mtoolbar = view.findViewById(R.id.toolbar);
        appBarLayout = view.findViewById(R.id.app_bar);
        linearLayout = view.findViewById(R.id.linear);
        title_collapsingbar_time = view.findViewById(R.id.title_collapsingbar_time);
        title_collapsingbar = view.findViewById(R.id.title_collapsingbar);
        recyclerViewAlarm = view.findViewById(R.id.recycleview_alarm);
    }
    //hàm được gọi khi nhận giá trị từ màn setalarm
    @Override
    public void onItemAlarmClickListener(int position,boolean isSelectedState) {
        if(!isSelectedState){
            Intent intent = new Intent(getActivity(),ActivitySetAlarm.class);
            intent.putExtra("position_need_edit",position);
            intent.putExtra("alarm",listAlarm.get(position));
            getParentFragment().startActivityForResult(intent,StaticName.CODE_EDIT_ALARM);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==StaticName.CODE_EDIT_ALARM && resultCode== Activity.RESULT_OK && data!=null){
            Alarm alarm = (Alarm) data.getSerializableExtra("data");
            int indexEdit = data.getIntExtra("position_need_edit",-1);
            if(indexEdit!=-1){
                //sửa báo thức
                listAlarm.set(indexEdit,alarm);
                mAdapterAlarm.notifyItemChanged(indexEdit);
            }else{
                //thêm báo thức
                alarm.setTurnOn(true);
                listAlarm.add(alarm);
                mAdapterAlarm.notifyItemInserted(listAlarm.size()-1);
            }
        }
    }
    public Alarm getDefaultAlarm(){
        Alarm alarm = new Alarm();
        alarm.setName("");
        alarm.setTurnOn(false);
        alarm.setSoundMode(SoundMode.getDefault(getContext()));
        alarm.setVibrateMode(new VibratePattern().getDefaut());
        alarm.setPauseMode(PauseMode.getDefault());
        return  alarm;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveDataToSharePreference();
    }

    public void saveDataToSharePreference(){
        if(listAlarm.size()!=0){
            Gson gson = new Gson();
            String json = gson.toJson(listAlarm);
            editor.putString(keySave, json);
            editor.apply();
        }
    }

    public List<Alarm> getDataSaved(){
        List<Alarm> arrayItems;
        String serializedObject = sharedPreferences.getString(keySave, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Alarm>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
            return arrayItems;
        }
        return null;
    }
}