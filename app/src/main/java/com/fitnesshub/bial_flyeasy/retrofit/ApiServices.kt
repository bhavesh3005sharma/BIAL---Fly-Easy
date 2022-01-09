package com.fitnesshub.bial_flyeasy.retrofit

import com.fitnesshub.bial_flyeasy.models.*
import io.reactivex.Flowable
import retrofit2.http.*

interface ApiServices {

    @POST("/user/login")
    fun signIn(@Body authModel: AuthModel): Flowable<ResourceResponse<UserModel>>

    @POST("/user/signup")
    fun signUp(@Body authModel: AuthModel): Flowable<ResourceResponse<UserModel>>

    @POST("/flight/search")
    fun searchFlights(@Body searchFlightModel: SearchFlightModel): Flowable<ResourceResponse<ArrayList<FlightModel>>>

    @PATCH("/user/{userId}")
    fun updateProfile(@Path("userId") id: String,@Body userModel: UserModel):Flowable<ResourceResponse<Unit>>

    @GET("/user/")
    fun getProfile(@Query("_id") id : String?): Flowable<ResourceResponse<UserModel>>

    @POST("/ticket")
    fun bookTicket(@Body ticketModel: TicketModel): Flowable<ResourceResponse<Unit>>

}