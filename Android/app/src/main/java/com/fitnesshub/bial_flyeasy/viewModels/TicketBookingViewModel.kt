package com.fitnesshub.bial_flyeasy.viewModels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.adapters.FoodItemsAdapter
import com.fitnesshub.bial_flyeasy.models.FoodItems
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.TicketModel
import com.fitnesshub.bial_flyeasy.repositories.TicketRepository
import com.fitnesshub.bial_flyeasy.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketBookingViewModel @Inject constructor(var ticketRepository: TicketRepository) : ViewModel() {
    private var ticketStatus = MediatorLiveData<ResourceResponse<Unit>>()

    fun bookTicket(ticketModel: TicketModel) {
        ticketStatus.value = ResourceResponse(Constants.IN_PROGRESS,null,null)
        ticketStatus.addSource(ticketRepository.bookTicket(ticketModel)){ticketStatus.value = it}
    }

    fun getFoodItems()  = ticketRepository.getFoodTicketItems()

    fun getTicketStatus() = ticketStatus

    fun getTicketBookingHistory() = ticketRepository. getTicketBookingHistory()

}