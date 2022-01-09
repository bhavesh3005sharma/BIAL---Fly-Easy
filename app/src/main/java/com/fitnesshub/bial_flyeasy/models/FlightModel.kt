package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable

data class FlightModel(
        val __v: Int = -1,
        val _id: String? = null,
        val arrivalTime: String? = null,
        val beltNumber: String? = null,
        val boardingTime: String? = null,
        val company: String? = null,
        val departureTime: String? = null,
        val destination: String? = null,
        val flightNo: String? = null,
        val gateNumber: String? = null,
        val source: String? = null,
        val status: String? = null,
        var selected: Boolean = false,
        val price: Int = 2514,
        val logo: String? = null
) : Serializable