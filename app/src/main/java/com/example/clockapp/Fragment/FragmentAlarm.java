package com.example.clockapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clockapp.Activity.ActivitySetAlarm;
import com.example.clockapp.Activity.MainActivity;
import com.example.clockapp.Adapter.AdapterAlarm;
import com.example.clockapp.Object.Alarm;
import com.example.clockapp.Object.PauseMode;
import com.example.clockapp.Object.Time;
import com.example.clockapp.Object.Util;
import com.example.clockapp.R;
import com.example.clockapp.Object.SoundMode;
import com.example.clockapp.Utils.StaticName;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAlarm extends Fragment implements AdapterAlarm.ItemAlarmListener, View.OnClickListener {
    LinearLayout linearCollapsing,linearActionDeleteItem,linearSelectAllItem,layoutActionDelete;
    AppBarLayout appBarLayout;
    TextView title_collapsingbar;
    RecyclerView recyclerViewAlarm;
    Toolbar mtoolbar;
    Menu menuToobar;
    ImageView imvSelect,imvDelete;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    List<Alarm> listAlarm ;
    AdapterAlarm mAdapterAlarm;
    final String dataStoreName = "AlarmDatabase";
    final String keySave = "listAlarmSaved";
    boolean isSelectAll = false;
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
        initView(view);
        //main

        ((MainActivity) getActivity()).setSupportActionBar(mtoolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.alarm_ring));
        //làm mờ chữ khi collap
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            float percentage = ((float) Math.abs(verticalOffset) / appBarLayout1.getTotalScrollRange());
            linearCollapsing.setAlpha(1- 2 * percentage);
            if(percentage>0.5) mtoolbar.setBackgroundColor(getContext().getResources().getColor(R.color.start_color));
            else mtoolbar.setBackgroundColor(getContext().getResources().getColor(R.color.transperant));
        });
        if(getDataSaved()!=null) listAlarm = getDataSaved();
        else listAlarm = new ArrayList<>();
        mAdapterAlarm = new AdapterAlarm(getContext(),listAlarm,this);
        recyclerViewAlarm.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAlarm.setAdapter(mAdapterAlarm);
        imvSelect.setOnClickListener(this);
        imvDelete.setOnClickListener(this);
        return view;
    }

        @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        menuToobar = menu;
    }
//xử lí menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_add:{
                Intent intent = new Intent(getActivity(), ActivitySetAlarm.class);
                intent.putExtra("position_need_edit",-1);
                intent.putExtra("alarm",getDefaultAlarm());
                getParentFragment().startActivityForResult(intent, StaticName.CODE_EDIT_ALARM);
                break;
            }
            case android.R.id.home:
                mAdapterAlarm.setAllItemCheck();

        }
        return super.onOptionsItemSelected(item);
    }

    public void initView(View view){
        mtoolbar = view.findViewById(R.id.toolbar);
        appBarLayout = view.findViewById(R.id.app_bar);
        linearCollapsing = view.findViewById(R.id.linear);
        title_collapsingbar = view.findViewById(R.id.title_collapsingbar);
        recyclerViewAlarm = view.findViewById(R.id.recycleview_alarm);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_tool_bar);
        tabLayout =  getParentFragment().getView().findViewById(R.id.tab_layout);
        viewPager = getActivity().findViewById(R.id.pager);
        linearActionDeleteItem = view.findViewById(R.id.bottom_action_delete);
        layoutActionDelete = view.findViewById(R.id.layout_action_delete);
        imvDelete = view.findViewById(R.id.imv_delete);
        imvSelect = view.findViewById(R.id.imv_select);
        linearSelectAllItem = view.findViewById(R.id.layout_select_all_item);
    }
    //sự kiện click item
    @Override
    public void onItemAlarmClickListener(int position,boolean isSelectedState) {
        //check / không check toàn bộ


        if(!mAdapterAlarm.isSelectState()){
            Intent intent = new Intent(getActivity(),ActivitySetAlarm.class);
            intent.putExtra("position_need_edit",position);
            intent.putExtra("alarm",listAlarm.get(position));
            getParentFragment().startActivityForResult(intent,StaticName.CODE_EDIT_ALARM);
        }else{
            if(listAlarm.size()==mAdapterAlarm.getSelectedItemsIds().size())
                imvSelect.setImageResource(R.drawable.ic_check);
            else
                imvSelect.setImageResource(R.drawable.ic_check_none);
            if(mAdapterAlarm.getSelectedItemsIds().size()==0){
                title_collapsingbar.setText("Chọn chuông báo");
                collapsingToolbarLayout.setTitle("Chọn chuông báo");
            }else{
                title_collapsingbar.setText("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
                collapsingToolbarLayout.setTitle("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
            }
        }
    }
    //sự kiện long click item
    @Override
    public void onItemAlarmLongClickListener() {
        stateSelect();
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
                if(listAlarm.get(indexEdit).isTurnOn())
                    Util.scheduleAlarm(getContext(),listAlarm.get(indexEdit));
                else
                    Util.cancelAlarm(getContext(),listAlarm.get(indexEdit).getId());
            }else{
                //thêm báo thức
                int indexAdd = findDuplicateAlarm(alarm);
                if(indexAdd==-1) {//nếu không trùng
                    alarm.setTurnOn(true);
                    listAlarm.add(alarm);
                    mAdapterAlarm.notifyItemInserted(listAlarm.size() - 1);
                    Util.scheduleAlarm(getContext(),listAlarm.get(listAlarm.size() - 1));
                }else{//nếu trùng
                    listAlarm.get(indexAdd).setTurnOn(true);
                    mAdapterAlarm.notifyItemChanged(indexAdd);
                    Util.scheduleAlarm(getContext(),listAlarm.get(indexAdd));
                }
            }
        }
        mAdapterAlarm.clearSelect();
    }
    //set giá trị mặc định cho một alarm
    public Alarm getDefaultAlarm(){
        Alarm alarm = new Alarm();
        alarm.setName("");
        alarm.setTurnOn(false);
        alarm.setSoundMode(SoundMode.getDefault(getContext()));
        alarm.setPauseMode(PauseMode.getDefault());
        return  alarm;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveDataToSharePreference();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xử lí sự kiện nút back
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(!mAdapterAlarm.isSelectState())
                    requireActivity().finish();
                mAdapterAlarm.setSelectState(false);
                mAdapterAlarm.notifyDataSetChanged();
                stateUnSelect();
                mAdapterAlarm.clearSelect();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }
    //lưu tới share preferences
    public void saveDataToSharePreference(){
        if(listAlarm.size()!=0){
            Gson gson = new Gson();
            String json = gson.toJson(listAlarm);
            editor.putString(keySave, json);
            editor.apply();
        }
    }
    //lấy những dữ liệu đã lưu trong sharepreference
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
    //trạng thái select item
    public void stateSelect(){
        linearActionDeleteItem.setVisibility(View.VISIBLE);
        menuToobar.findItem(R.id.ic_add).setVisible(false);
        linearSelectAllItem.setVisibility(View.VISIBLE);
        if(mAdapterAlarm.getSelectedItemsIds().size() == listAlarm.size()){
            imvSelect.setImageResource(R.drawable.ic_check);
            collapsingToolbarLayout.setTitle("Tất cả"+ mAdapterAlarm.getSelectedItemsIds().size());
        }else
            imvSelect.setImageResource(R.drawable.ic_check_none);
        title_collapsingbar.setText("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
        collapsingToolbarLayout.setTitle("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
        tabLayout.setVisibility(View.GONE);
        viewPager.setUserInputEnabled(false);
        //tạo animation cho imv xóa
        Animation flyAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.fly);
        layoutActionDelete.startAnimation(flyAnimation);
    }
    //trạng thái bình thường
    public void stateUnSelect(){
        linearActionDeleteItem.setVisibility(View.GONE);
        menuToobar.findItem(R.id.ic_add).setVisible(true);
        collapsingToolbarLayout.setTitle("Chuông báo");
        title_collapsingbar.setText("Ngủ đủ giấc để có một cơ thể khỏe mạnh");
        linearSelectAllItem.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setUserInputEnabled(true);
        if (viewPager.isFakeDragging())
            viewPager.endFakeDrag();
    }
//  click
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imv_delete){
            SparseBooleanArray itemSelectId = mAdapterAlarm.getSelectedItemsIds();
            if(itemSelectId.size()==listAlarm.size()){
                listAlarm.clear();
            }else{
                //tạo list chứa các index đã chon
                List<Integer> list = new ArrayList<>();
                for(int i=0;i<itemSelectId.size();i++){
                    if(itemSelectId.valueAt(i))
                        list.add(itemSelectId.keyAt(i));
                }
                Collections.sort(list);
                //xoá các phần tử tìm được
                if(list.size()!=0){
                    for (int i = list.size() - 1; i >= 0; i--) {
                        int index = list.get(i);
                        listAlarm.remove(index);
                    }
                }
            }
            mAdapterAlarm.setSelectState(false);
            mAdapterAlarm.clearSelect();
            stateUnSelect();
            }
        else if(v.getId()==R.id.imv_select){
            if(isSelectAll) {
                imvSelect.setImageResource(R.drawable.ic_check_none);
                mAdapterAlarm.clearSelect();
                isSelectAll= false;
                collapsingToolbarLayout.setTitle("Chọn chuông báo");
                title_collapsingbar.setText("Chọn chuông báo");
            }
            else {
                imvSelect.setImageResource(R.drawable.ic_check);
                mAdapterAlarm.setAllItemCheck();
                isSelectAll=true;
                collapsingToolbarLayout.setTitle("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
                title_collapsingbar.setText("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
            }
        }
    }
    public int findDuplicateAlarm(Alarm alarm){
        for(int i=0;i<listAlarm.size();i++){
            if(listAlarm.get(i).getTime().getHour()==alarm.getTime().getHour()&&
                listAlarm.get(i).getTime().getMinute()==alarm.getTime().getMinute()) return i;
        }
        return  -1;
    }
}