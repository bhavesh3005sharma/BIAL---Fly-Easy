package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable
import java.util.*

data class SearchFlightModel(
        var dates: ArrayList<String>,
        var destination: String,
        var source: String,
        var date: String,
        var singleWay: Boolean = true
) : Serializable