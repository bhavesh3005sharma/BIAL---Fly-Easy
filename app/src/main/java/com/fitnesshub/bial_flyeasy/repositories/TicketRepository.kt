package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.fitnesshub.bial_flyeasy.database.Preferences
import com.fitnesshub.bial_flyeasy.models.FoodItems
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.TicketModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TicketRepository @Inject constructor(var apiServices: ApiServices,var prefs : Preferences) {
    fun bookTicket(ticketModel: TicketModel) : LiveData<ResourceResponse<Unit>> {
        ticketModel.userId = prefs.user._id
        return LiveDataReactiveStreams.fromPublisher(apiServices.bookTicket(ticketModel)
            // instead of calling onError, do this
            .onErrorReturn { t: Throwable? ->
                val error = ResourceResponse<Unit>(Constants.ERROR, null, t?.message)
                error
            }
            .subscribeOn(Schedulers.io()))
    }

    fun getFoodTicketItems() =
         LiveDataReactiveStreams.fromPublisher(apiServices.getFoodTicketItems()
            // instead of calling onError, do this
            .onErrorReturn { t: Throwable? ->
                ResourceResponse<List<FoodItems>>(Constants.ERROR, null, t?.message)
            }
            .subscribeOn(Schedulers.io()))

    fun getTicketBookingHistory(): LiveData<ResourceResponse<ArrayList<TicketModel>>> {
        val response = MediatorLiveData<ResourceResponse<ArrayList<TicketModel>>>()
        response.value = ResourceResponse(Constants.IN_PROGRESS, null, null)
        val source = LiveDataReactiveStreams.fromPublisher(apiServices.getTicketBookingHistory(prefs.user._id)
            // instead of calling onError, do this
            .onErrorReturn { t: Throwable? ->
                val errorResponse = ResourceResponse<ArrayList<TicketModel>>(Constants.ERROR, null, t?.message)
                errorResponse
            }
            .subscribeOn(Schedulers.io()))
        response.addSource(source) {
            response.value = it
        }
        return response
    }

}