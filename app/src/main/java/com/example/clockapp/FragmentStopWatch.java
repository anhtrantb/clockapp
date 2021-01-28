package com.example.clockapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentStopWatch extends Fragment {

    private static FragmentStopWatch fragmentStopWatch;
    public static FragmentStopWatch getInstance(){
        if(fragmentStopWatch==null){
            fragmentStopWatch = new FragmentStopWatch();
        }
        return fragmentStopWatch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stop_watch, container, false);
    }
}