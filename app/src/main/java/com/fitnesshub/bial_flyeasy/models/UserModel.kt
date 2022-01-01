package com.fitnesshub.bial_flyeasy.models

data class UserModel(
        var _id: String,
        var email: String,
        var password: String? = null,
        var profileCompleted: Boolean? = false,
        var name: String? = null,
        var phone: String? = null,
        var aadharNo: String? = null,
        var gender: String? = null,
        var homeCityAddress: String? = null,
        var age: Int = 0
)