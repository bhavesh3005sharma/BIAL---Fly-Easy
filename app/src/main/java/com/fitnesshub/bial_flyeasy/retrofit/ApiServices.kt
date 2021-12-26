package com.fitnesshub.bial_flyeasy.retrofit

import com.fitnesshub.bial_flyeasy.models.BaseModel
import com.fitnesshub.bial_flyeasy.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/signIn")
    fun signIn(@Query("email") email: String?, @Query("password") password: String?): Call<UserModel>?

    @GET("/signUp")
    fun signUp(@Query("email") email: String?, @Query("password") password: String?): Call<BaseModel>

}