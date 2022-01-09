package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable

data class TicketModel(
    var __v: Int? = null,
    var userId : String? = null,
    var _id: String? = null,
    var booking_user_id: String? = null,
    var checkList: List<CheckList>? = null,
    var datesOfJourney: List<String>? = null,
    var flightNos: List<String>? = null,
    var flights: List<FlightModel>? = null,
    var food_details: List<FoodItems>? = null,
    var passengers: List<Passenger>? = null
) : Serializable