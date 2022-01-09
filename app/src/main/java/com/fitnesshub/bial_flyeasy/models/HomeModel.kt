package com.fitnesshub.bial_flyeasy.models

data class HomeModel(
    var wifi_password:String?=null,
    var guidelines:List<String>?=null,
    var arrival_flights:List<FlightModel>?=null,
    var departure_flights:List<FlightModel>?=null,
    var latest_ticket:TicketModel?=null
)
