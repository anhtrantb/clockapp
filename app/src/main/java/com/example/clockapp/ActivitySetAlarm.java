package com.example.clockapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.clockapp.Base.BaseAdapter;
import com.example.clockapp.Base.ItemViewClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ActivitySetAlarm extends AppCompatActivity implements ItemViewClickListener, View.OnClickListener {
TimePicker timePicker;
TextView mtxt_escape, mtxt_save;
RecyclerView recyclerView;
ImageButton mDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        initView();

        timePicker.setIs24HourView(true);
        mtxt_escape.setOnClickListener(v->finish());
        mDatePicker.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemViewClickListener(int position, List<?> list) {

    }
    public void initView(){
        timePicker = findViewById(R.id.time_picker);
        mtxt_escape = findViewById(R.id.txt_escape);
        mtxt_save = findViewById(R.id.txt_save);
        mDatePicker = findViewById(R.id.date_picker);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_picker:{
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        }
                    },2021,2,2);
                datePickerDialog.show();
            }
        }
    }
}