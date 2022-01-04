package com.fitnesshub.bial_flyeasy.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.R
import com.fitnesshub.bial_flyeasy.databinding.ActivityFlightBookingBinding
import com.fitnesshub.bial_flyeasy.models.SearchFlightModel
import com.fitnesshub.bial_flyeasy.utils.HelperClass
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_flight_booking.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

class FlightBookingActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityFlightBookingBinding
    private var not_chosen = "Not Chosen"
    private var dates: ArrayList<String> = arrayListOf(not_chosen, not_chosen)
    private var searchFlightModel: MutableLiveData<SearchFlightModel> = MutableLiveData(
            SearchFlightModel(dates, "MAA", "BIAL", "", true))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_flight_booking)
        departure_date.setOnClickListener { openDatePicker(true) }
        return_date.setOnClickListener { openDatePicker(false) }
        switchIsRoundTrip.setOnCheckedChangeListener { _, checked ->
            run {
                searchFlightModel.value?.singleWay = !checked
                updateSearchFlightModel()
            }
        }
        setupToolbar()
        swap_stations.setOnClickListener { swapStation() }
        searchFlightModel.observe(this, { binding.flight = it })
        binding.submitButton.setOnClickListener {
            val data = searchFlightModel.value
            if (data == null || data.source == null || data.destination == null || data.dates[0] == not_chosen || (!data.singleWay && data.dates[1] == not_chosen)) {
                HelperClass.toast(this, "Provide all details to search flight")
                return@setOnClickListener
            }
            searchFlight()
        }

    }

    private fun setupToolbar() {
        ivBack.setOnClickListener { onBackPressed() }
        tvToolbarTitle.text = resources.getString(R.string.title_activity_flight_booking)
        ivNotificatons.setOnClickListener {
            // TODO: "go to notifications page"
        }
    }

    private fun swapStation() {
        val s = searchFlightModel.value?.source
        searchFlightModel.value?.source = searchFlightModel.value?.destination!!
        searchFlightModel.value?.destination = s!!
        updateSearchFlightModel()
    }

    private fun updateSearchFlightModel() {
        searchFlightModel.value = searchFlightModel.value // to notify change in searchFlightModel
    }

    private fun openDatePicker(isDepartureDate: Boolean) {
        val datePickerDialog = DatePickerDialog.newInstance(
                this,
                Calendar.getInstance().get(Calendar.YEAR), // Initial year selection
                Calendar.getInstance().get(Calendar.MONTH), // Initial month selection
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) // Initial day selection
        )
//        datePickerDialog.minDate = Calendar.getInstance()
        datePickerDialog.accentColor = resources.getColor(R.color.blue)
        datePickerDialog.setOkColor(resources.getColor(R.color.blue))
        datePickerDialog.setCancelColor(resources.getColor(R.color.blue))
        var tag = "Return_Date"
        if (isDepartureDate) tag = "Departure_Date"
        datePickerDialog.show(supportFragmentManager, tag)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (view?.tag == "Departure_Date") {
            searchFlightModel.value?.dates?.set(0, "$year-${monthOfYear + 1}-$dayOfMonth")
            searchFlightModel.value?.dates?.set(1, not_chosen)
        } else searchFlightModel.value?.dates?.set(1, "$year-${monthOfYear + 1}-$dayOfMonth")
        updateSearchFlightModel()
    }

    fun searchFlight() {
        val intent = Intent(this, SearchFlightActivity::class.java)
        intent.putExtra("search_flight_model", searchFlightModel.value)
        startActivity(intent)
    }

}