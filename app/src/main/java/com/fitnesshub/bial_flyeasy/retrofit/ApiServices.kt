package com.fitnesshub.bial_flyeasy.retrofit

import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import io.reactivex.Flowable
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @POST("/user/login")
    fun signIn(@Query("email") email: String?, @Query("password") password: String?): Flowable<ResourceResponse<UserModel>>

    @POST("/user/signup")
    fun signUp(@Query("email") email: String?, @Query("password") password: String?): Flowable<ResourceResponse<UserModel>>

}