package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.database.Preferences
import com.fitnesshub.bial_flyeasy.models.AuthModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthRepository @Inject constructor(var apiServices: ApiServices, var prefs: Preferences) {
    val userLoggedIn: Boolean = prefs.isUserLoggedIn

    @JvmField
    val statusLiveData = MediatorLiveData<ResourceResponse<UserModel>>()
    private val loadingStatus = MutableLiveData<ResourceResponse<UserModel>>()
    val response: LiveData<ResourceResponse<UserModel>>
        get() = statusLiveData

    init {
        statusLiveData.addSource(loadingStatus) { statusLiveData.value = loadingStatus.value }
    }

    fun signIn(email: String?, password: String?) {
        loadingStatus.value = ResourceResponse(Constants.IN_PROGRESS, null, null)

        val source = LiveDataReactiveStreams.fromPublisher(apiServices.signIn(AuthModel(email, password))
                // instead of calling onError, do this
                .onErrorReturn { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                }
                .subscribeOn(Schedulers.io()))

        statusLiveData.addSource(source) {
            statusLiveData.value = it
            if (it.status == 200 && it.data?._id != null) {
                // Save user data in local db & set as logged in
                prefs.isUserLoggedIn = true
                prefs.token = it.token
                prefs.SetUserData(it.data)
            }
        }
    }

    fun signUp(email: String?, password: String?) {
        loadingStatus.value = ResourceResponse(Constants.IN_PROGRESS, null, null)

        val source = LiveDataReactiveStreams.fromPublisher(apiServices.signUp(AuthModel(email, password))
                // instead of calling onError, do this
                .onErrorReturn { t: Throwable? ->
                    val errorUser = ResourceResponse<UserModel>(Constants.ERROR, null, t?.message)
                    errorUser
                }
                .map {
                    val data = it
                    if (data.status == Constants.OKAY) data.status = Constants.REGISTRATION_SUCCESS
                    data
                }
                .subscribeOn(Schedulers.io()))

        statusLiveData.addSource(source) { statusLiveData.value = it }
    }

    fun getUser() = prefs.user
}