package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthRepository @Inject constructor(var apiServices: ApiServices) {

    @JvmField
    val statusLiveData = MediatorLiveData<ResourceResponse<UserModel>>()
    val response: LiveData<ResourceResponse<UserModel>>
        get() = statusLiveData

    fun signIn(email: String?, password: String?) {
        statusLiveData.value = ResourceResponse(Constants.IN_PROGRESS, null, null)

        val source = LiveDataReactiveStreams.fromPublisher(apiServices.signIn(email, password)
                // instead of calling onError, do this
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))

        /* TODO : Save User Data in local DB
        if(source.value?.status == 200 && source.value?.data?._id!=null){
            // Save user data in local db & set as logged in
            Prefs.setUserLoggedIn(context,true)
            Prefs.setToken(context,source.value?.token)
            Prefs.SetUserData(context,source.value?.data)
            Log.i("AuthRepository", "signIn: data saved tolocal db "+source.value?.data.toString())
        }
        */

        statusLiveData.addSource(source) { statusLiveData.value = source.value }
    }

    fun signUp(email: String?, password: String?) {
        statusLiveData.value = ResourceResponse(Constants.IN_PROGRESS, null, null)

        val source = LiveDataReactiveStreams.fromPublisher(apiServices.signUp(email, password)
                // instead of calling onError, do this
                .onErrorReturn(Function { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                })
                .subscribeOn(Schedulers.io()))

        statusLiveData.addSource(source) { statusLiveData.value = source.value }
    }
}