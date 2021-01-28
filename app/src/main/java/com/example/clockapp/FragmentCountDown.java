package com.example.clockapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCountDown extends Fragment {

    private static FragmentCountDown fragmentCountDown;
    public static FragmentCountDown getInstance(){
        if(fragmentCountDown==null){
            fragmentCountDown = new FragmentCountDown();
        }
        return fragmentCountDown;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_count_down, container, false);
    }
}