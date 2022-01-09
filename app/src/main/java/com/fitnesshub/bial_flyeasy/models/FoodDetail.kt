package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable

data class FoodDetail(
    val _id: String,
    val name: String,
    val quantity: Int
) : Serializable