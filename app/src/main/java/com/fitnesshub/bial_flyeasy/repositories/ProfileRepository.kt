package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.database.Prefs
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileRepository @Inject constructor(var apiServices: ApiServices,var prefs: Prefs) {
    @JvmField
    var userData:MutableLiveData<ResourceResponse<UserModel>> = MutableLiveData()

    init {
        userData.value?.data =prefs.getUser()
    }

    fun updateProfile(userModel: UserModel):LiveData<ResourceResponse<Unit>>{
        var liveData=MutableLiveData<ResourceResponse<Unit>>(
            ResourceResponse(Constants.IN_PROGRESS,null,null))
        val source=LiveDataReactiveStreams.fromPublisher(apiServices.updateProfile(userModel)
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<Unit>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))
        if(source.value?.status==200){
            prefs.SetUserData(userModel)
            userData.value?.data=userModel
        }
        liveData.value = source.value
        return liveData
    }

    fun getUserData():LiveData<ResourceResponse<UserModel>>{
        var liveData=MutableLiveData<ResourceResponse<UserModel>>(
            ResourceResponse(Constants.IN_PROGRESS, null, null))
        val source=LiveDataReactiveStreams.fromPublisher(apiServices.getProfile(prefs.user._id)
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))
        if(source.value?.status==200){
            prefs.SetUserData(source.value?.data)
            userData.value = source.value
        }
        liveData.value=source.value
        return liveData
    }
}