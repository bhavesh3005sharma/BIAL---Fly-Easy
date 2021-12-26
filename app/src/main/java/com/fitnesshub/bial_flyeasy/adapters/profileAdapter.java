package com.fitnesshub.bial_flyeasy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesshub.bial_flyeasy.models.CheckList;

import java.util.ArrayList;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.holder> {
    ArrayList<CheckList> list;
    Context context;
    CheckListListener listener;

    public profileAdapter(ArrayList<CheckList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setUpOnDeleteListener(profileAdapter.CheckListListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public profileAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new profileAdapter.holder(LayoutInflater.from(parent.getContext()).inflate(com.fitnesshub.bial_flyeasy.R.layout.layout_checklist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull profileAdapter.holder holder, int position) {
        CheckList checkList=list.get(position);
        holder.item.setText(checkList.getItem());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)listener.deleteClicked(checkList,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public interface CheckListListener {
        void deleteClicked(CheckList checkList,int position);
    }

    public static class holder extends RecyclerView.ViewHolder{
        TextView item;
        CheckBox checkBox;
        ImageView delete;

        public holder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(com.fitnesshub.bial_flyeasy.R.id.item);
            checkBox=itemView.findViewById(com.fitnesshub.bial_flyeasy.R.id.checkBox);
            delete=itemView.findViewById(com.fitnesshub.bial_flyeasy.R.id.delete);

            checkBox.setVisibility(View.GONE);
        }
    }
}
