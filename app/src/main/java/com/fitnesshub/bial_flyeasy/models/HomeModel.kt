package com.fitnesshub.bial_flyeasy.models

data class HomeModel(
    var wifi_password:String?=null,
    var guidelines:ArrayList<String>?=null,
    var arrival_flights:ArrayList<FlightModel>?=null,
    var departure_flights:ArrayList<FlightModel>?=null,
    var latest_ticket:TicketModel?=null
)
