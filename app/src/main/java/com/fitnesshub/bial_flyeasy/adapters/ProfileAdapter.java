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

import com.fitnesshub.bial_flyeasy.databinding.LayoutChecklistBinding;
import com.fitnesshub.bial_flyeasy.models.CheckList;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.viewHolder> {
    ArrayList<CheckList> list;
    Context context;
    CheckListListener listener;

    public ProfileAdapter(ArrayList<CheckList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setUpOnDeleteListener(ProfileAdapter.CheckListListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public ProfileAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutChecklistBinding binding = LayoutChecklistBinding.inflate(LayoutInflater.from(context),parent,false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.viewHolder holder, int position) {
        holder.binding.setChecklist(list.get(position));
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public interface CheckListListener {
        void deleteClicked(CheckList checkList,int position);
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        LayoutChecklistBinding binding;

        public viewHolder(@NonNull LayoutChecklistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.checkBox.setVisibility(View.GONE);
        }
    }
}
