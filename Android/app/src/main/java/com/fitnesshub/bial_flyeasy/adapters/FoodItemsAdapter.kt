package com.fitnesshub.bial_flyeasy.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitnesshub.bial_flyeasy.databinding.LayoutFoodItemTicketBinding
import com.fitnesshub.bial_flyeasy.models.FoodItems

class FoodItemsAdapter : RecyclerView.Adapter<FoodItemsAdapter.FoodItemViewHolder>() {

    var listFoodItems = emptyList<FoodItems>()

    class FoodItemViewHolder(var binding : LayoutFoodItemTicketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FoodItems){
            binding.foodItem = item
            binding.tvAdd.setOnClickListener{
                item.quantity++
                binding.tvQuantity.text = item.quantity.toString()
            }
            binding.tvSubtract.setOnClickListener{
                if(item.quantity==0) return@setOnClickListener
                item.quantity--
                binding.tvQuantity.text = item.quantity.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoodItemViewHolder(LayoutFoodItemTicketBinding.inflate(
            LayoutInflater.from(parent.context), parent,false
        ))

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        holder.bind(listFoodItems[position])
    }

    override fun getItemCount() = listFoodItems.size

    fun setFoodItemsList(foodDetails: List<FoodItems>){
        Log.i("Adapter", "setFoodItemsList: $foodDetails")
        listFoodItems = foodDetails
        notifyDataSetChanged()
    }
}