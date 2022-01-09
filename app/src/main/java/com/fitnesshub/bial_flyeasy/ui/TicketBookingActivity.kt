package com.fitnesshub.bial_flyeasy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fitnesshub.bial_flyeasy.R
import com.fitnesshub.bial_flyeasy.databinding.ActivityTicketBookingBinding
import com.fitnesshub.bial_flyeasy.models.TicketModel
import com.fitnesshub.bial_flyeasy.utils.Constants
import com.fitnesshub.bial_flyeasy.utils.HelperClass
import com.fitnesshub.bial_flyeasy.viewModels.AuthViewModel
import com.fitnesshub.bial_flyeasy.viewModels.TicketBookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketBookingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTicketBookingBinding
    private lateinit var viewModel : TicketBookingViewModel
    private lateinit var ticketModel: TicketModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ticket_booking)
        viewModel = ViewModelProvider(this).get(TicketBookingViewModel::class.java)
        ticketModel = intent.getSerializableExtra("ticketModel") as TicketModel

        binding.ticket = ticketModel
        binding.status = Constants.USELESS

        binding.submitButton.setOnClickListener{
            viewModel.bookTicket(ticketModel)
        }

        viewModel.getTicketStatus().observe(this,{
            binding.status = it.status
            if(it.status!=Constants.IN_PROGRESS) HelperClass.toast(this,it.message)
        })
    }
}