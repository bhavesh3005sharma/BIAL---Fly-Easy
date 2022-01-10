package com.fitnesshub.bial_flyeasy.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fitnesshub.bial_flyeasy.R
import com.fitnesshub.bial_flyeasy.adapters.FlightsAdapter
import com.fitnesshub.bial_flyeasy.databinding.ActivityTicketBookingHistoryBinding
import com.fitnesshub.bial_flyeasy.models.FlightModel
import com.fitnesshub.bial_flyeasy.models.TicketModel
import com.fitnesshub.bial_flyeasy.models.UIUpdatesModel
import com.fitnesshub.bial_flyeasy.utils.Constants
import com.fitnesshub.bial_flyeasy.utils.HelperClass
import com.fitnesshub.bial_flyeasy.viewModels.TicketBookingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class TicketBookingHistoryActivity : AppCompatActivity(), FlightsAdapter.ItemClickListener {
    lateinit var binding: ActivityTicketBookingHistoryBinding
    lateinit var viewModel : TicketBookingViewModel
    private lateinit var adapter: FlightsAdapter
    private var flights = ArrayList<FlightModel>()
    private var uiUpdates = UIUpdatesModel(status = Constants.IN_PROGRESS)
    private var ticketList = mutableListOf<TicketModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBookingHistoryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(TicketBookingViewModel::class.java)
        setupToolbar()

        adapter = FlightsAdapter(this, flights, this)
        binding.rvFlights.adapter = adapter
        binding.updates = uiUpdates

        viewModel.getTicketBookingHistory().observe(this, {
            uiUpdates = if (it == null) UIUpdatesModel(Constants.ERROR, "API Response is null")
            else if (it.status == Constants.OKAY) {
                if (it.data.isNullOrEmpty()) UIUpdatesModel(status = Constants.NO_DATA_FOUND)
                else {
                    ticketList = it.data!!
                    val list = mutableListOf<FlightModel>()
                    for(ticket in it.data!!){
                        if(ticket.datesOfJourney.isNullOrEmpty()) continue
                        var ind = 0
                        for(date in ticket.datesOfJourney!!){
                            ticket.flights?.get(ind)?.flightDate = date
                            ticket.flights?.get(ind)?.ticketId = ticket._id
                            list.add(ticket.flights?.get(ind++)!!)
                        }
                    }

                    flights.clear()
                    flights.addAll(list)
                    adapter.notifyDataSetChanged()
                    UIUpdatesModel(status = Constants.OKAY)
                }
            } else UIUpdatesModel(it.status, it.message)
            binding.updates = uiUpdates
        })
    }

    private fun setupToolbar() {
        ivBack.setOnClickListener { onBackPressed() }
        tvToolbarTitle.text = resources.getString(R.string.title_activity_ticket_booking_history)
        ivNotificatons.setOnClickListener {
            // TODO: "go to notifications page"
        }
    }

    override fun onFlightClick(newSelectedFlightPosition: Int, lastSelectedFlightPosition: Int) {
        for(ticket in ticketList){
            if(ticket._id==flights[newSelectedFlightPosition].ticketId){
                val intent = Intent(this,TicketBookingActivity::class.java)
                intent.putExtra("ticketModel",ticket)
                intent.putExtra("useActivityFor",Constants.BOOKED_TICKET_VIEW)
                startActivity(intent)
                return
            }
        }
        HelperClass.toast(this,"Sorry Ticket Not Found")
    }
}