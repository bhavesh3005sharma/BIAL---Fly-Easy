package com.fitnesshub.bial_flyeasy.viewModels

import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.models.SearchFlightModel
import com.fitnesshub.bial_flyeasy.repositories.FlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightListViewModel @Inject constructor(var repository: FlightRepository) : ViewModel() {
    fun getFlights(searchFlightModel: SearchFlightModel) = repository.getFlights(searchFlightModel)
}