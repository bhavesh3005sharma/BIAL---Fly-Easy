package com.fitnesshub.bial_flyeasy.retrofit

import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.Profile
import com.fitnesshub.bial_flyeasy.models.UserModel
import io.reactivex.Flowable
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServices {

    @POST("/user/login")
    fun signIn(@Query("email") email: String?, @Query("password") password: String?): Flowable<ResourceResponse<UserModel>>

    @POST("/user/signup")
    fun signUp(@Query("email") email: String?, @Query("password") password: String?): Flowable<ResourceResponse<UserModel>>

    @PUT("/updateProfile")
    fun updateProfile(@Body()profile: Profile):Call<Int>


}