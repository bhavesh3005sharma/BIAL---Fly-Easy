package com.fitnesshub.bial_flyeasy.models

data class HomeModel(
    var wifi_password:String,
    var guidelines:ArrayList<String>,
    var arrival_flights:ArrayList<FlightModel>,
    var departure_flights:ArrayList<FlightModel>,
    //var latest_ticket:Ticket
)
