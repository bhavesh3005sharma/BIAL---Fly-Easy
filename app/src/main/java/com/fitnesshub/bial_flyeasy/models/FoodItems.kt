package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable

data class FoodItems(
    val _id: String,
    val name: String,
    var quantity: Int,
    var editable: Boolean = false
) : Serializable