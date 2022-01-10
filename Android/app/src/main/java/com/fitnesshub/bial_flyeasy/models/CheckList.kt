package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable

data class CheckList(
    val _id: String,
    val content: String,
    val isChecked: Boolean
) : Serializable