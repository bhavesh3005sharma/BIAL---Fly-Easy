package com.fitnesshub.bial_flyeasy.retrofit

import com.fitnesshub.bial_flyeasy.models.AuthModel
import com.fitnesshub.bial_flyeasy.models.HomeModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("/user/login")
    fun signIn(@Body authModel: AuthModel): Flowable<ResourceResponse<UserModel>>

    @POST("/user/signup")
    fun signUp(@Body authModel: AuthModel): Flowable<ResourceResponse<UserModel>>

    @PATCH("/user/{id}")
    fun updateProfile(@Path("id")_id:String?,@Body()userModel: UserModel):Flowable<ResourceResponse<Unit>>

    @GET("/user")
    fun getProfile(@Query("_id") id : String?): Flowable<ResourceResponse<UserModel>>

    @POST("/user/home")
    fun getData(@Field("userId")id: String?,@Field("airport")airport:String?):Flowable<ResourceResponse<HomeModel>>

}