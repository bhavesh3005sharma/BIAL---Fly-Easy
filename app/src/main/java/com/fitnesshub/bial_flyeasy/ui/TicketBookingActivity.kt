package com.fitnesshub.bial_flyeasy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ticket_booking)
        viewModel = ViewModelProvider(this).get(TicketBookingViewModel::class.java)
        ticketModel = intent.getSerializableExtra("ticketModel") as TicketModel

        binding.ticket = ticketModel
        binding.status = Constants.IN_PROGRESS
        binding.rvFeedItems.adapter = foodAdapter
        binding.foodRVDropDown = true
        setupToolbar()

        binding.submitButton.setOnClickListener {
            binding.status = Constants.IN_PROGRESS
            ticketModel.food_details = foodAdapter.listFoodItems
            viewModel.bookTicket(ticketModel)
        }

        binding.ivDropFoodItem.setOnClickListener {binding.foodRVDropDown = !(binding.foodRVDropDown as Boolean)}

        viewModel.getTicketStatus().observe(this,{
            binding.status = it.status
            if(it.status!=Constants.IN_PROGRESS) HelperClass.toast(this,it.message)
        })

        viewModel.getFoodItems().observe(this,{
            binding.status = it.status
            if(it.data!=null) foodAdapter.setFoodItemsList(it.data!!)
        })
    }

    private fun setupToolbar() {
        ivBack.setOnClickListener { onBackPressed() }
        tvToolbarTitle.text = resources.getString(R.string.title_activity_ticket_booking)
        ivNotificatons.setOnClickListener {
            // TODO: "go to notifications page"
        }
    }
}