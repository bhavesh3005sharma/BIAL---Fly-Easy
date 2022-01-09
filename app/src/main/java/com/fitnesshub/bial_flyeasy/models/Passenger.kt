package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable

data class Passenger(
    val _id: String,
    val age: Int,
    val gender: String,
    val name: String
) : Serializable