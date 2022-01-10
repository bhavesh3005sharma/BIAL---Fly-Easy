package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.models.FlightModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.SearchFlightModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FlightRepository @Inject constructor(var apiServices: ApiServices) {

    fun getFlights(searchFlightModel: SearchFlightModel): MutableLiveData<ResourceResponse<ArrayList<FlightModel>>> {
        val response = MediatorLiveData<ResourceResponse<ArrayList<FlightModel>>>()
        response.value = ResourceResponse(Constants.IN_PROGRESS, null, null)
        val source = LiveDataReactiveStreams.fromPublisher(apiServices.searchFlights(searchFlightModel)
                // instead of calling onError, do this
                .onErrorReturn { t: Throwable? ->
                    val errorResponse = ResourceResponse<ArrayList<FlightModel>>(Constants.ERROR, null, t?.message)
                    errorResponse
                }
                .subscribeOn(Schedulers.io()))
        response.addSource(source) {
            response.value = it
        }
        return response
    }
}