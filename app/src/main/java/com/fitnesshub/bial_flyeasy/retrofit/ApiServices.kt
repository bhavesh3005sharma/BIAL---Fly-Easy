package com.fitnesshub.bial_flyeasy.retrofit

interface ApiServices {

    fun signIn(email: String?, password: String?)

    fun signUp(email: String?, password: String?)

}