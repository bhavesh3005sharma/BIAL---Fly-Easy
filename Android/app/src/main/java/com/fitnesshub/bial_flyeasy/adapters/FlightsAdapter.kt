package com.fitnesshub.bial_flyeasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitnesshub.bial_flyeasy.databinding.LayoutFlightBinding
import com.fitnesshub.bial_flyeasy.models.FlightModel

class FlightsAdapter(private val context: Context, private val list: List<FlightModel>,
                     private val listener: ItemClickListener) : RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {

    class ViewHolder(var layoutFlightBinding: LayoutFlightBinding) : RecyclerView.ViewHolder(layoutFlightBinding.root)

    var selectedFlightPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutFlightBinding = LayoutFlightBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(layoutFlightBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.layoutFlightBinding.flight = item
        holder.layoutFlightBinding.root.setOnClickListener {
            listener.onFlightClick(position, selectedFlightPosition)
            selectedFlightPosition = position
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ItemClickListener {
        fun onFlightClick(newSelectedFlightPosition: Int, lastSelectedFlightPosition: Int)
    }
}