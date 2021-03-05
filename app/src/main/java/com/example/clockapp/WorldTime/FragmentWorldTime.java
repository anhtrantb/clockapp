package com.example.clockapp.WorldTime;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clockapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
public class FragmentWorldTime extends Fragment implements StartDragListener {
    RecyclerView recycleWorldTime;
    Adapter adapter;
    ItemTouchHelper touchHelper;
    ArrayList<String> list = new ArrayList<>();

    private static FragmentWorldTime fragmentWorldTime;
    public static FragmentWorldTime getInstance(){
        if(fragmentWorldTime==null){
            fragmentWorldTime = new FragmentWorldTime();
        }
        return fragmentWorldTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_world_time, container, false);
        recycleWorldTime = view.findViewById(R.id.recycle_world_time);
        createList();
        adapter = new Adapter(list,this);
        recycleWorldTime.setAdapter(adapter);
        recycleWorldTime.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recycleWorldTime.setLayoutManager(new LinearLayoutManager(getContext()));
        //thiết lập sự kiện di chuyển của item trong recycle view
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recycleWorldTime);
        return view;
    }

    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        //khi người dùng chạm vào icon
        //điểm neo để kéo
        touchHelper.startDrag(viewHolder);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void createList(){
        java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone("GMT+1");
        Calendar currentTime = Calendar.getInstance(timeZone);
        currentTime.get(Calendar.YEAR);
        currentTime.get(Calendar.MONTH);
        currentTime.get(Calendar.DAY_OF_MONTH);
        currentTime.get(Calendar.HOUR);
        currentTime.get(Calendar.MINUTE);

        Log.e("tag",currentTime.get(Calendar.HOUR)+"/"+
        currentTime.get(Calendar.MINUTE)+"/"+currentTime.get(Calendar.YEAR)+"/"+
        currentTime.get(Calendar.MONTH)+"/"+
        currentTime.get(Calendar.DATE)+" ");
        for(int i=-6;i<=10;i++){
            list.add(getTimeFromTimeZone(i)+" "+ getCountry(i));
        }
    }
    private  String getTimeFromTimeZone(int gmt){
        java.util.TimeZone timeZone;
        if(gmt>=0)
            timeZone = java.util.TimeZone.getTimeZone("GMT+"+gmt);
        else
            timeZone = java.util.TimeZone.getTimeZone("GMT"+gmt);
        Calendar calendar = Calendar.getInstance(timeZone);
        return calendar.get(Calendar.HOUR)+":"+
                calendar.get(Calendar.MINUTE)+"  " +
                calendar.get(Calendar.DATE)+"/" +
                (calendar.get(Calendar.MONTH)+1)+"/"+
                calendar.get(Calendar.YEAR);
    }
    private  String getCountry(int gmt){
        switch (gmt){
            case -6:return "Canada";
            case -5:return "Columbia";
            case -4:return "Paraguay";
            case -3:return "Greenland";
            case -2:return "Brazil";
            case -1:return "Cape Verde";
            case 0:return "England";
            case 1:return "Algeria";
            case 2:return "Denmark";
            case 3:return "Israel";
            case 4:return "Oman";
            case 5:return "Pakistan";
            case 6:return "Russia";
            case 7:return "VietNam";
            case 8:return "Australia";
            case 9: return "Indonesia";
            case 10:return "Antarctica";
            default:return "none";
        }
    }
}