package com.fitnesshub.bial_flyeasy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesshub.bial_flyeasy.databinding.CardArrivalDepartureBinding;
import com.fitnesshub.bial_flyeasy.databinding.CardFoodStoreBinding;
import com.fitnesshub.bial_flyeasy.models.FoodStoreModel;

import java.util.ArrayList;
import java.util.List;

public class FoodStoreAdapter extends RecyclerView.Adapter<FoodStoreAdapter.viewHolder> {
    List<FoodStoreModel> list;
    Context context;
    ShopListener mListener;

    public FoodStoreAdapter(Context context, List<FoodStoreModel> list){
        this.context=context;
        this.list=list;
    }

    public void setUpOnCardListener(FoodStoreAdapter.ShopListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardFoodStoreBinding binding=CardFoodStoreBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FoodStoreAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FoodStoreModel model=list.get(position);
        holder.cardBinding.setFoodstoreModel(model);
        holder.cardBinding.cardFoodStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null)mListener.cardClicked(model,position);
            }
        });
    }

    @Override
    public int getItemCount() {return (list != null) ? list.size() : 0;}

    public interface ShopListener {
        void cardClicked(FoodStoreModel foodStoreModel, int position);
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        CardFoodStoreBinding cardBinding;

        public viewHolder(CardFoodStoreBinding cardBinding) {
            super(cardBinding.getRoot());
            this.cardBinding=cardBinding;
        }
    }
}
