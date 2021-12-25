package com.fitnesshub.bial_flyeasy.repositories

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants

class AuthRepository(var apiServices: ApiServices) {
    @JvmField
    val statusLiveData = MutableLiveData<String>()
    val response: LiveData<String>
        get() = statusLiveData

    fun signIn(email: String?, password: String?) {
        apiServices.signIn(email, password)
        statusLiveData.postValue(Constants.IN_PROGRESS.toString())
        Handler().postDelayed({ statusLiveData.postValue(Constants.USER_NOT_FOUND.toString()) }, 2000)
    }

    fun signUp(email: String?, password: String?) {
        apiServices.signUp(email, password)
        statusLiveData.postValue(Constants.IN_PROGRESS.toString())
        Handler().postDelayed({ statusLiveData.postValue(Constants.OKAY.toString()) }, 2000)
    }
}