package com.fitnesshub.bial_flyeasy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitnesshub.bial_flyeasy.R;
import com.fitnesshub.bial_flyeasy.adapters.ArrivalDepartureAdapter;
import com.fitnesshub.bial_flyeasy.databinding.LayoutArrivalDepartureBinding;
import com.fitnesshub.bial_flyeasy.models.FlightModel;

import java.util.ArrayList;
import java.util.List;

public class ArrivalDepartureFragment extends Fragment {
    List<FlightModel> flight;
    Context context;
    LayoutArrivalDepartureBinding layoutBinding;
    boolean isArrival;

    public ArrivalDepartureFragment(Context context,List<FlightModel> flight, boolean isArrival){
        this.context=context;
        this.flight=flight;
        this.isArrival=isArrival;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding=DataBindingUtil.inflate(inflater,R.layout.layout_arrival_departure,container,false);
        View view = layoutBinding.getRoot();
        layoutBinding.setIsArrival(isArrival);
        layoutBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ArrivalDepartureAdapter adapter=new ArrivalDepartureAdapter(context,flight,isArrival);
        layoutBinding.recyclerView.setAdapter(adapter);
        return view;
    }

}
