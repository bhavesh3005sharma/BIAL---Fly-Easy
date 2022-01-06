package com.fitnesshub.bial_flyeasy.models

data class TicketModel(
    val __v: Int,
    val _id: String,
    val booking_user_id: String,
    val checkList: List<CheckList>,
    val datesOfJourney: List<String>,
    val flightNos: List<String>,
    val flights: List<FlightModel>,
    val food_details: List<List<FoodDetail>>,
    val passengers: List<Passenger>
)
