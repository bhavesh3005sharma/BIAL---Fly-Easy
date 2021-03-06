package com.fitnesshub.bial_flyeasy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.databinding.CardArrivalDepartureBinding;
import com.fitnesshub.bial_flyeasy.models.FlightModel;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArrivalDepartureAdapter extends RecyclerView.Adapter<ArrivalDepartureAdapter.viewHolder> {
    List<FlightModel> list;
    Context context;
    boolean isArrival;

    public ArrivalDepartureAdapter(Context context,List<FlightModel> list,boolean isArrival){
        this.context=context;
        this.list=list;
        this.isArrival=isArrival;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardArrivalDepartureBinding binding=CardArrivalDepartureBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.cardBinding.setIsArrival(isArrival);
        holder.cardBinding.setFlight(list.get(position));
        if(isArrival) holder.cardBinding.time.setText(HelperClass.getTime(list.get(position).getArrivalTime()));
        else holder.cardBinding.time.setText(HelperClass.getTime(list.get(position).getDepartureTime()));
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        CardArrivalDepartureBinding cardBinding;

        public viewHolder(CardArrivalDepartureBinding cardArrivalDepartureBinding) {
            super(cardArrivalDepartureBinding.getRoot());
            this.cardBinding=cardArrivalDepartureBinding;
        }
    }

}
