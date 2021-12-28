package com.fitnesshub.bial_flyeasy.repositories

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.models.Profile
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileRepository @Inject constructor(var apiServices: ApiServices) {
    @JvmField
    val statusLiveData = MutableLiveData<ResourceResponse<UserModel>>()
    val response: LiveData<ResourceResponse<UserModel>>
        get() = statusLiveData


    fun updateProfile(userModel: UserModel){
        statusLiveData.value= ResourceResponse(Constants.IN_PROGRESS,null,null)
        val source=LiveDataReactiveStreams.fromPublisher(apiServices.updateProfile(userModel)
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))
        statusLiveData.value = source.value
    }
}