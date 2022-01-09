package com.fitnesshub.bial_flyeasy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fitnesshub.bial_flyeasy.R
import com.fitnesshub.bial_flyeasy.adapters.FoodItemsAdapter
import com.fitnesshub.bial_flyeasy.databinding.ActivityTicketBookingBinding
import com.fitnesshub.bial_flyeasy.models.FoodItems
import com.fitnesshub.bial_flyeasy.models.TicketModel
import com.fitnesshub.bial_flyeasy.utils.Constants
import com.fitnesshub.bial_flyeasy.utils.HelperClass
import com.fitnesshub.bial_flyeasy.viewModels.AuthViewModel
import com.fitnesshub.bial_flyeasy.viewModels.TicketBookingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class TicketBookingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTicketBookingBinding
    private lateinit var viewModel : TicketBookingViewModel
    private lateinit var ticketModel: TicketModel
    private val foodAdapter = FoodItemsAdapter()
    private var status = Constants.USELESS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ticket_booking)
        viewModel = ViewModelProvider(this).get(TicketBookingViewModel::class.java)
        ticketModel = intent.getSerializableExtra("ticketModel") as TicketModel
        status = intent.getIntExtra("useActivityFor",Constants.USELESS)
        Log.i("TicketBookingActivity", "onCreate: $ticketModel")

        binding.rvFeedItems.adapter = foodAdapter
        setupToolbar()

        binding.ivDropFoodItem.setOnClickListener {binding.foodRVDropDown = !(binding.foodRVDropDown as Boolean)}

        if(status==Constants.BOOKED_TICKET_VIEW){
            binding.status = Constants.USELESS
            binding.foodRVDropDown = false
            binding.status = status
            if(!ticketModel.food_details.isNullOrEmpty())
            foodAdapter.setFoodItemsList(ticketModel.food_details!!)
            ticketModel.flights?.get(0)?.flightDate = HelperClass.getDate(ticketModel.flights?.get(0)?.flightDate)
            if(ticketModel.flights?.size!! >1)
                ticketModel.flights?.get(1)?.flightDate = HelperClass.getDate(ticketModel.flights?.get(1)?.flightDate)
        }else {
            binding.status = Constants.IN_PROGRESS
            binding.foodRVDropDown = true

            viewModel.getTicketStatus().observe(this, {
                binding.status = it.status
                if (it.status != Constants.IN_PROGRESS) HelperClass.toast(this, it.message)
                if(it.status==Constants.OKAY) {
                    if(ticketModel.food_details!=null) for(item in ticketModel.food_details!!) item.editable = false
                    val intent = Intent(this,TicketBookingActivity::class.java)
                    intent.putExtra("ticketModel",ticketModel)
                    intent.putExtra("useActivityFor",Constants.BOOKED_TICKET_VIEW)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            })

            viewModel.getFoodItems().observe(this, {
                binding.status = it.status
                if (it.data != null) {
                    for(item in it.data!!) item.editable = true
                    foodAdapter.setFoodItemsList(it.data!!)
                }
            })

            binding.submitButton.setOnClickListener {
                binding.status = Constants.IN_PROGRESS
                ticketModel.food_details = foodAdapter.listFoodItems
                viewModel.bookTicket(ticketModel)
            }
        }

        binding.ticket = ticketModel
    }

    private fun setupToolbar() {
        ivBack.setOnClickListener { onBackPressed() }
        tvToolbarTitle.text = if(status==Constants.BOOKED_TICKET_VIEW)
            resources.getString(R.string.title_activity_ticket_details)
        else resources.getString(R.string.title_activity_ticket_booking)
        ivNotificatons.setOnClickListener {
            // TODO: "go to notifications page"
        }
    }
}