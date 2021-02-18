package com.example.clockapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

public class FragmentAlarm extends Fragment implements AdapterAlarm.ItemAlarmListener {
    LinearLayout linearLayout;
    AppBarLayout appBarLayout;
    TextView title_collapsingbar_time,title_collapsingbar;
    RecyclerView recyclerViewAlarm;
    Toolbar mtoolbar;

    ArrayList<Alarm> listAlarm = new ArrayList<>();
    AdapterAlarm mAdapterAlarm;
    private static FragmentAlarm fragmentAlarm;
    public static FragmentAlarm getInstance(){
       if(fragmentAlarm==null){
           fragmentAlarm = new FragmentAlarm();
       }
       return fragmentAlarm;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        //find view
        findview(view);
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
        //hiển thị recycle view
        Alarm testAlarm = new Alarm();
        testAlarm.setTurnOn(true);
        Time testTime = new Time();
        testAlarm.setTime(testTime);
        listAlarm.add(testAlarm);
        listAlarm.add(testAlarm);

        mAdapterAlarm = new AdapterAlarm(listAlarm,this);
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
                getParentFragment().startActivityForResult(intent,StaticName.CODE_ADD_ALARM);
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

    public void findview(View view){
        mtoolbar = view.findViewById(R.id.toolbar);
        appBarLayout = view.findViewById(R.id.app_bar);
        linearLayout = view.findViewById(R.id.linear);
        title_collapsingbar_time = view.findViewById(R.id.title_collapsingbar_time);
        title_collapsingbar = view.findViewById(R.id.title_collapsingbar);
        recyclerViewAlarm = view.findViewById(R.id.recycleview_alarm);
    }
    //hàm được gọi khi nhận giá trị từ màn setalarm
    public void updateData(Intent data){
        if(data!=null){
            Alarm alarm = (Alarm) data.getSerializableExtra("data");
            //thêm một báo thức vào trong list
            listAlarm.add(alarm);
            mAdapterAlarm.notifyItemInserted(listAlarm.size()-1);
        }
    }

    @Override
    public void onItemAlarmClickListener(int position) {
        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}