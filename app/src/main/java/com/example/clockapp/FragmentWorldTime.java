package com.example.clockapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentWorldTime extends Fragment {
    private static FragmentWorldTime fragmentWorldTime;
    public static FragmentWorldTime getInstance(){
        if(fragmentWorldTime==null){
            fragmentWorldTime = new FragmentWorldTime();
        }
        return fragmentWorldTime;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_time, container, false);
    }
}