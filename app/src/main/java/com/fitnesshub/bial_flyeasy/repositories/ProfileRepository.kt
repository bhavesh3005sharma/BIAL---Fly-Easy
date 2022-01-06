package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
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
    var userData=MediatorLiveData<ResourceResponse<UserModel>>()
    init {
        initUserDataFromPrefs()
    }

    private fun initUserDataFromPrefs() {
        userData.value = ResourceResponse(Constants.USELESS,prefs.user,null)
    }

    fun updateProfile(userModel: UserModel) : MediatorLiveData<ResourceResponse<Unit>>{
        val liveData=MediatorLiveData<ResourceResponse<Unit>>()
        liveData.value = ResourceResponse(Constants.IN_PROGRESS,null,null)
        val source=LiveDataReactiveStreams.fromPublisher(apiServices.updateProfile(userModel._id,userModel)
                .onErrorReturn { t: Throwable? ->
                    val errorUser = ResourceResponse<Unit>(Constants.ERROR, null, t?.message)
                    errorUser
                }
            .subscribeOn(Schedulers.io()))

        liveData.addSource(source){
            liveData.value = source.value
            if(source.value?.status==200){
                prefs.SetUserData(userModel)
            }
        }
        return liveData
    }

    fun syncUserData(){
        val source=LiveDataReactiveStreams.fromPublisher(apiServices.getProfile(prefs.user._id)
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))
        userData.addSource(source){
            userData.value=source.value
            if(source.value?.status==200){
                prefs.SetUserData(source.value?.data)
            }
        }
    }

    fun getUserData() = userData
    fun getCityInt() = prefs.cityInt
}