package com.fitnesshub.bial_flyeasy.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.fitnesshub.bial_flyeasy.fragments.ArrivalDepartureFragment;
import com.fitnesshub.bial_flyeasy.models.FlightModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private Context context;
    List<FlightModel> arrival,departure;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager,int behaviour, Context context, List<FlightModel> arr, List<FlightModel> depar) {
        super(fragmentManager, behaviour);
        this.arrival=arr;
        this.departure=depar;
        this.tabCount=behaviour;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ArrivalDepartureFragment(context,arrival,true);
            case 1:
                return new ArrivalDepartureFragment(context,departure,false);
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull @NotNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Arrival";
            case 1:
                return "Departure";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
