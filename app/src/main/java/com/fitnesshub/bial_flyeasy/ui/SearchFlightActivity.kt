package com.fitnesshub.bial_flyeasy.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.R
import com.fitnesshub.bial_flyeasy.adapters.PageViewAdapter
import com.fitnesshub.bial_flyeasy.databinding.ActivitySearchFlightBinding
import com.fitnesshub.bial_flyeasy.models.FlightModel
import com.fitnesshub.bial_flyeasy.models.SearchFlightModel
import com.fitnesshub.bial_flyeasy.models.TicketModel
import com.fitnesshub.bial_flyeasy.utils.HelperClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search_flight.*
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class SearchFlightActivity : AppCompatActivity() {

    private lateinit var searchFlightModel: SearchFlightModel
    private lateinit var binding: ActivitySearchFlightBinding
    private var flights = MutableLiveData(arrayListOf(FlightModel(_id = "-1"), FlightModel(_id = "-1")))
    private var TAG_ONE_WAY_FRAG = "TAG_ONE_WAY_FRAG"
    private var TAG_ROUND_TRIP_FRAG = "TAG_ROUND_TRIP_FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_flight)
        searchFlightModel = intent.getSerializableExtra("search_flight_model") as SearchFlightModel

        binding.searchFlightModel = searchFlightModel
        flights.observe(this, { binding.flights = it })

        setUpViewPager()
        tabLayout.setupWithViewPager(viewpager)
        setupToolbar()

        binding.fabGoToNextPage.setOnClickListener {
            if(flights.value?.get(0)?._id=="-1") {
                HelperClass.toast(this,"Select Flight")
                return@setOnClickListener
            }

            val ticketModel = TicketModel()
            if(searchFlightModel.singleWay) {
                ticketModel.flightNos = listOf(flights.value?.get(0)?.flightNo!!)
                ticketModel.datesOfJourney = listOf(searchFlightModel.dates[0])
            }else {
                if(flights.value?.get(1)?._id=="-1") {
                    HelperClass.toast(this,"Select Flight")
                    return@setOnClickListener
                }
                ticketModel.flightNos = listOf(flights.value?.get(0)?.flightNo!!,flights.value?.get(1)?.flightNo!!)
                ticketModel.datesOfJourney = listOf(searchFlightModel.dates[0],searchFlightModel.dates[1])
            }
            ticketModel.flights = flights.value
            val intent = Intent(this,TicketBookingActivity::class.java)
            intent.putExtra("ticketModel",ticketModel)
            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        ivBack.setOnClickListener { onBackPressed() }
        tvToolbarTitle.text = resources.getString(R.string.title_activity_flight_booking)
        ivNotificatons.setOnClickListener {
            // TODO: "go to notifications page"
        }
    }

    private fun setUpViewPager() {
        val pageViewAdapter = PageViewAdapter(supportFragmentManager, 0)
        val searchFlightModelFirst = searchFlightModel.copy()
        searchFlightModelFirst.date = searchFlightModelFirst.dates[0]
        val flightListFrag1 = FlightListFragment.newInstance(searchFlightModelFirst, TAG_ONE_WAY_FRAG)
        pageViewAdapter.addFragment(flightListFrag1, "Departure : "+searchFlightModelFirst.date)

        if (!searchFlightModel.singleWay) {
            val searchFlightModelSecond = searchFlightModel.copy()
            swapStation(searchFlightModelSecond)
            searchFlightModelSecond.date = searchFlightModelSecond.dates[1]
            val flightListFrag2 = FlightListFragment.newInstance(searchFlightModelSecond, TAG_ROUND_TRIP_FRAG)
            pageViewAdapter.addFragment(flightListFrag2, "Return : "+searchFlightModelSecond.date)
        }
        viewpager.adapter = pageViewAdapter
    }

    fun updateSelectedFlightDetails(flightModel: FlightModel, tag: String) {
        if (tag == TAG_ONE_WAY_FRAG) {
            flightModel.flightDate = searchFlightModel.dates[0]
            flights.value?.set(0, flightModel)
        } else {
            flightModel.flightDate = searchFlightModel.dates[1]
            flights.value?.set(1, flightModel)
        }
        flights.value = flights.value
    }

    private fun swapStation(searchFlightModel: SearchFlightModel) {
        val s = searchFlightModel.source
        searchFlightModel.source = searchFlightModel.destination
        searchFlightModel.destination = s
    }
}