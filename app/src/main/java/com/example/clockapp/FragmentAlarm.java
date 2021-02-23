package com.example.clockapp;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    LinearLayout linearCollapsing,linearActionDeleteItem,linearSelectAllItem;
    AppBarLayout appBarLayout;
    TextView title_collapsingbar_time,title_collapsingbar;
    RecyclerView recyclerViewAlarm;
    Toolbar mtoolbar;
    Menu menuToobar;
    ImageView imvSelect;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ImageView imvDelete;
    List<Alarm> listAlarm ;
    AdapterAlarm mAdapterAlarm;
    final String dataStoreName = "listAlarmData";
    final String keySave = "keySave";
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
        title_collapsingbar.setText("Chuông báo sau 18 giờ 1 phút");
        title_collapsingbar_time.setText("06:00, T.6, 29 Th1");

        ((MainActivity) getActivity()).setSupportActionBar(mtoolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.alarm_ring));
        //làm mờ chữ khi collap
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            float percentage = ((float) Math.abs(verticalOffset) / appBarLayout1.getTotalScrollRange());
            linearCollapsing.setAlpha(1- 2 * percentage);
        });
        if(getDataSaved()!=null) listAlarm = getDataSaved();
        else listAlarm = new ArrayList<>();
        mAdapterAlarm = new AdapterAlarm(getContext(),listAlarm,this);
        recyclerViewAlarm.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAlarm.setAdapter(mAdapterAlarm);
        imvDelete.setOnClickListener(this);
        imvSelect.setOnClickListener(this);
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
            case android.R.id.home:
                mAdapterAlarm.setAllItemCheck();

        }
        return super.onOptionsItemSelected(item);
    }

    public void initView(View view){
        mtoolbar = view.findViewById(R.id.toolbar);
        appBarLayout = view.findViewById(R.id.app_bar);
        linearCollapsing = view.findViewById(R.id.linear);
        title_collapsingbar_time = view.findViewById(R.id.title_collapsingbar_time);
        title_collapsingbar = view.findViewById(R.id.title_collapsingbar);
        recyclerViewAlarm = view.findViewById(R.id.recycleview_alarm);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_tool_bar);
        tabLayout =  getParentFragment().getView().findViewById(R.id.tab_layout);
        viewPager = getActivity().findViewById(R.id.pager);
        linearActionDeleteItem = view.findViewById(R.id.layout_action_delete);
        imvDelete = view.findViewById(R.id.imv_action_delete);
        imvSelect = view.findViewById(R.id.imv_select);
        linearSelectAllItem = view.findViewById(R.id.layout_select_all_item);
    }
    //sự kiện click item
    @Override
    public void onItemAlarmClickListener(int position,boolean isSelectedState) {
        //check / không check toàn bộ
        if(listAlarm.size()==mAdapterAlarm.getSelectedItemsIds().size())
            imvSelect.setImageResource(R.drawable.ic_check);
        else
            imvSelect.setImageResource(R.drawable.ic_check_none);

        if(!mAdapterAlarm.isSelectState()){
            Intent intent = new Intent(getActivity(),ActivitySetAlarm.class);
            intent.putExtra("position_need_edit",position);
            intent.putExtra("alarm",listAlarm.get(position));
            getParentFragment().startActivityForResult(intent,StaticName.CODE_EDIT_ALARM);
        }else{
            title_collapsingbar.setText("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
            collapsingToolbarLayout.setTitle("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
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
            }else{
                //thêm báo thức
                alarm.setTurnOn(true);
                listAlarm.add(alarm);
                mAdapterAlarm.notifyItemInserted(listAlarm.size()-1);
            }
        }
    }
    //set giá trị mặc định cho một alarm
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //xử lí sự kiện nút back
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.e("tag","back_in_fragment_alarm");
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
        menuToobar.findItem(R.id.ic_menu).setVisible(false);
        linearSelectAllItem.setVisibility(View.VISIBLE);
        if(mAdapterAlarm.getSelectedItemsIds().size() == listAlarm.size()){
            imvSelect.setImageResource(R.drawable.ic_check);
            collapsingToolbarLayout.setTitle("Tất cả"+ mAdapterAlarm.getSelectedItemsIds().size());
        }else
            imvSelect.setImageResource(R.drawable.ic_check_none);
        title_collapsingbar.setText("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
        collapsingToolbarLayout.setTitle("Đã chọn "+ mAdapterAlarm.getSelectedItemsIds().size());
        title_collapsingbar_time.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setUserInputEnabled(false);
    }
    //trạng thái bình thường
    public void stateUnSelect(){
        linearActionDeleteItem.setVisibility(View.GONE);
        menuToobar.findItem(R.id.ic_add).setVisible(true);
        menuToobar.findItem(R.id.ic_menu).setVisible(true);
        collapsingToolbarLayout.setTitle("Chuông báo");
        title_collapsingbar_time.setVisibility(View.VISIBLE);
        title_collapsingbar.setText("Chuông báo sau 18 giờ 1 phút");
        title_collapsingbar_time.setText("06:00, T.6, 29 Th1");
        linearSelectAllItem.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setUserInputEnabled(true);
        if (viewPager.isFakeDragging())
            viewPager.endFakeDrag();
    }
//  click
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imv_action_delete){
            SparseBooleanArray itemSelectId = mAdapterAlarm.getSelectedItemsIds();
            mAdapterAlarm.setSelectState(false);
            stateUnSelect();
            for (int i = (itemSelectId.size() - 1); i >= 0; i--) {
                if (itemSelectId.valueAt(i)) {
                    listAlarm.remove(itemSelectId.keyAt(i));
                    mAdapterAlarm.clearSelect();
                    mAdapterAlarm.notifyDataSetChanged();
                }
            }
            stateUnSelect();
        }else if(v.getId()==R.id.imv_select){
            if(isSelectAll) {
                imvSelect.setImageResource(R.drawable.ic_check_none);
                mAdapterAlarm.clearSelect();
                isSelectAll= false;
            }
            else {
                imvSelect.setImageResource(R.drawable.ic_check);
                mAdapterAlarm.setAllItemCheck();
                isSelectAll=true;
            }
        }
    }
}