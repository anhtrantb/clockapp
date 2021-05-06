package com.example.clockapp.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.clockapp.R;
import com.example.clockapp.WorldTime.Adapter;
import com.example.clockapp.WorldTime.CountryUtil;
import com.example.clockapp.WorldTime.ItemMoveCallback;
import com.example.clockapp.WorldTime.StartDragListener;


public class FragmentWorldTime extends Fragment implements TextWatcher, StartDragListener {
    RecyclerView recycleWorldTime;
    Adapter adapter;
    EditText inputSearch;
    ItemTouchHelper touchHelper;
    CountryUtil countryUtil = new CountryUtil();

    private static FragmentWorldTime fragmentWorldTime;
    static public FragmentWorldTime getInstance(){
        if(fragmentWorldTime==null) fragmentWorldTime = new FragmentWorldTime();
        return fragmentWorldTime;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_world_time,container,false);
        // Inflate the layout for this fragment
        recycleWorldTime = view.findViewById(R.id.recycle_world_time);
        inputSearch = view.findViewById(R.id.input_search);
        inputSearch.addTextChangedListener(this);
        createList();
        adapter = new Adapter(countryUtil.getCountryList(),this);
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

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.updateList(countryUtil.searchByCityOrCountry(s.toString()));
    }
    //hiển thị lại danh sách khi người dùng nhập dữ liệu
    @Override
    public void afterTextChanged(Editable s) {
    }
}