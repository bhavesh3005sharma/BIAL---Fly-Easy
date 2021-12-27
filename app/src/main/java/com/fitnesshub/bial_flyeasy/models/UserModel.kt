package com.fitnesshub.bial_flyeasy.models

data class UserModel(
        var _id: String,
        var email: String,
        var password: String? = null,
        var profileCompleted: Boolean? = false,
        var name: String? = null,
        var phone_no: String? = null,
        var aadharcard_no: String? = null,
        var gender: String? = null,
        var address: String? = null,
        var age: Int
)