package com.fitnesshub.bial_flyeasy.retrofit

import com.fitnesshub.bial_flyeasy.models.*
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("/user/login")
    fun signIn(@Body authModel: AuthModel): Flowable<ResourceResponse<UserModel>>

    @POST("/user/signup")
    fun signUp(@Body authModel: AuthModel): Flowable<ResourceResponse<UserModel>>

    @POST("/flight/search")
    fun searchFlights(@Body searchFlightModel: SearchFlightModel): Flowable<ResourceResponse<ArrayList<FlightModel>>>

}