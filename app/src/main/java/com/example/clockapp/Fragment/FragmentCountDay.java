package com.example.clockapp.Fragment;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.appota.lunarcore.LunarCoreHelper;
import com.example.clockapp.Object.Time;
import com.example.clockapp.R;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FragmentCountDay extends Fragment {
    Spinner mSpinnerToDay;
    Button btnDisplay;
    TextView mTvResult;
    EditText edtToDay;
    TextView edtFromDay;
    ImageView imvPickDate;
    Time timeFrom,timeTo;
    final String[] listToDay= {"Hôm nay","Tết dương","Tết âm","Giỗ tổ","8/3","30/4 - 1/5","20/10","20/11","Noel","Trung thu"};
    final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
   private  static FragmentCountDay fragmentCountDay;
   public  static  FragmentCountDay getInstance(){
       if(fragmentCountDay==null) fragmentCountDay = new FragmentCountDay();
       return fragmentCountDay;
   }
   
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_count_day, container, false);
        initView(view);
        initSpinner();
        timeFrom = Time.getCurrentTime();
        timeFrom.setMonth(timeFrom.getMonth()+1);
        edtFromDay.setOnClickListener(v->openDatePicker(true));
        edtToDay.setOnClickListener(v->mSpinnerToDay.performClick());
        btnDisplay.setOnClickListener(v-> {
            long day = countDay(timeFrom,timeTo);
            if(day == 0L) mTvResult.setText("hiện tại");
            if(day<0) mTvResult.setText("đã qua "+ (-day)+ " ngày");
            else mTvResult.setText("còn "+ day+ " ngày");
        });
        imvPickDate.setOnClickListener(v->openDatePicker(false));
        //spinner cho chọn ngày kết thúc
        mSpinnerToDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        timeTo = Time.getCurrentTime();
                        timeTo.setMonth(timeTo.getMonth()+1);
                        break;//hôm nay
                    case 1: //tết dương
                        setTimeDay(1,1,currentYear,false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                    case 2://tết âm
                        int[] lunarDay = LunarCoreHelper.convertLunar2Solar(1, 1, currentYear+1,0, 7);
                        setTimeDay(lunarDay[0],lunarDay[1],lunarDay[2],false);
                        break;
                    case 3: //10/3 giỗ tổ
                        int[] solar2 = LunarCoreHelper.convertLunar2Solar(10, 3, currentYear,0, 7);
                        setTimeDay(solar2[0],solar2[1],solar2[2],false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                    case 4: //8/3
                        setTimeDay(8,3,currentYear,false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                    case 5://30/4
                        setTimeDay(30,4,currentYear,false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                    case 6://20/10
                        setTimeDay(20,10,currentYear,false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                    case 7://20/11
                        setTimeDay(20,11,currentYear,false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                    case 8://25/12
                        setTimeDay(25,12,currentYear,false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                    case 9://trung thu
                        int[] tetTrungThu = LunarCoreHelper.convertLunar2Solar(15, 8, currentYear,0, 7);
                        setTimeDay(tetTrungThu[0],tetTrungThu[1],tetTrungThu[2],false);
                        if(checkBeforeDay(timeTo)) timeTo.setYear(currentYear+1);
                        break;
                }
                edtToDay.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.getSelectedItem().toString();
            }
        });
        return  view;
   }


    private void initView(View view) {
        mSpinnerToDay = view.findViewById(R.id.spinner_to_day);
        mTvResult = view.findViewById(R.id.tv_result);
        btnDisplay = view.findViewById(R.id.btn_display);
        edtFromDay = view.findViewById(R.id.edt_spinner_from_day);
        edtToDay = view.findViewById(R.id.edt_spinner_to_day);
        imvPickDate = view.findViewById(R.id.imv_pick_date);
    }
    private void initSpinner(){
        ArrayAdapter<String> adapterToDay = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listToDay
        );
        mSpinnerToDay.setAdapter(adapterToDay);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void openDatePicker(boolean isFromDay){
        Time timeCurrent = Time.getCurrentTime();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    setTimeDay(dayOfMonth,month+1,year,isFromDay);
                },timeCurrent.getYear(),timeCurrent.getMonth(),timeCurrent.getDay()
        );
        datePickerDialog.show();
    }
    public void setTimeDay(int day, int month, int year, boolean isFrom){
        if(isFrom){
            timeFrom.setDay(day);
            timeFrom.setMonth(month);
            timeFrom.setYear(year);
            if(Time.getCurrentTime().getDay()==day&&(Time.getCurrentTime().getMonth()==month-1)&&currentYear==year)
                edtFromDay.setText("Hôm nay");
            else
                edtFromDay.setText(day+"/"+month+"/"+year);
        }else{
            timeTo.setDay(day);
            timeTo.setMonth(month);
            timeTo.setYear(year);
            edtToDay.setText(day+"/"+month+"/"+year);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  long countDay(Time t1, Time t2){
        LocalDate start = LocalDate.of(t1.getYear(),t1.getMonth(),t1.getDay());
        LocalDate end = LocalDate.of(t2.getYear(),t2.getMonth(),t2.getDay());
        return ChronoUnit.DAYS.between(start, end);
    }
    public boolean checkBeforeDay(Time time){
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(time.getYear(),time.getMonth()-1,time.getDay());
        return  calendar.before(now);
    }
}