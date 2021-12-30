package com.fitnesshub.bial_flyeasy.repositories

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.database.Prefs
import com.fitnesshub.bial_flyeasy.models.AuthModel
import com.fitnesshub.bial_flyeasy.models.Profile
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileRepository @Inject constructor(var apiServices: ApiServices,var prefs: Prefs) {
    @JvmField
    val statusLiveData = MutableLiveData<ResourceResponse<UserModel>>()
    val response: LiveData<ResourceResponse<UserModel>>
        get() = statusLiveData


    fun updateProfile(userModel: UserModel):LiveData<ResourceResponse<Unit>>{
        var liveData=MutableLiveData<ResourceResponse<Unit>>()
        statusLiveData.value= ResourceResponse(Constants.IN_PROGRESS,null,null)
        val source=LiveDataReactiveStreams.fromPublisher(apiServices.updateProfile(userModel)
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<Unit>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))
        if(source.value?.status==200){
            prefs.SetUserData(userModel)
            liveData.value = source.value
        }
        return liveData
    }

    fun getUserData(userModel: UserModel):MediatorLiveData<ResourceResponse<UserModel>>{
        var liveData=MediatorLiveData<ResourceResponse<UserModel>>()
        val source=LiveDataReactiveStreams.fromPublisher(apiServices.getProfile(userModel)
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))
        if(source.value?.status==200){
            prefs.SetUserData(userModel)
            liveData.value = source.value
        }
        return liveData
    }
}