package com.example.clockapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clockapp.R;


public class FragmentUtility extends Fragment {

    private  static FragmentUtility fragmentUtility;
    public static FragmentUtility getInstance() {
      if(fragmentUtility ==null){
          fragmentUtility = new FragmentUtility();
      }
        return fragmentUtility;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utility, container, false);
    }
}