package com.example.clockapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockapp.Interface.InterfaceRadioCheckedListener;
import com.example.clockapp.Object.ItemSelect;
import com.example.clockapp.R;

import java.util.List;

public class AdapterSelectItem  extends RecyclerView.Adapter<AdapterSelectItem.ViewHolder> {
    private List<ItemSelect> listItem;
    private int positionChecked=0;
    boolean isClickable= true;
    //interface cho sự kiện click
    public interface OnItemRadioCheck {
        void onItemRadioChecked(int position);
    }

    public AdapterSelectItem(List<ItemSelect> listItem,  OnItemRadioCheck mOnRingtoneListener,int positionChecked) {
        this.listItem = listItem;
        this.positionChecked = positionChecked;
        this.mOnRingtoneListener = mOnRingtoneListener;
        listItem.get(positionChecked).setChecked(true);
    }

    private OnItemRadioCheck mOnRingtoneListener;
    public AdapterSelectItem(List<ItemSelect> listItem, OnItemRadioCheck mOnRingtoneListener) {
        this.listItem = listItem;
        this.mOnRingtoneListener = mOnRingtoneListener;
    }
    public void setItemClickable(boolean status){
        this.isClickable = status;
    }

    @NonNull
    @Override
    public AdapterSelectItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_content,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnRingtoneListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelectItem.ViewHolder holder, int position) {
       holder.tvContent.setText(listItem.get(position).getContent());
       if(positionChecked==position){
           holder.radioSelect.setChecked(true);
       }else{
           holder.radioSelect.setChecked(false);
       }
       holder.setRadioCheckListener(new InterfaceRadioCheckedListener() {
           @Override
           public void onRadioChecked(int position) {
               if(position != positionChecked){
                   //thay đổi trạng thái của radiobutton
                   listItem.get(positionChecked).setChecked(false);
                   listItem.get(position).setChecked(true);
                   positionChecked = position;
                   notifyDataSetChanged();
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    RadioButton radioSelect;
    TextView tvContent;
        OnItemRadioCheck onItemRadioCheck;
        InterfaceRadioCheckedListener radioCheckedListener;
        public ViewHolder(@NonNull View itemView, OnItemRadioCheck mOnRingtoneListener ) {
            super(itemView);
            initViewHolder(itemView);
            this.onItemRadioCheck = mOnRingtoneListener;
            itemView.setOnClickListener(this);
        }
        public void initViewHolder(View view){
            radioSelect = view.findViewById(R.id.radio_select);
            tvContent = view.findViewById(R.id.tv_content);
        }
        public void setRadioCheckListener(InterfaceRadioCheckedListener radioCheckListener){
            this.radioCheckedListener = radioCheckListener;
        }

        @Override
        public void onClick(View v) {
            if(!isClickable) return;
            onItemRadioCheck.onItemRadioChecked(getAdapterPosition());
            radioCheckedListener.onRadioChecked(getAdapterPosition());
        }
    }
}
