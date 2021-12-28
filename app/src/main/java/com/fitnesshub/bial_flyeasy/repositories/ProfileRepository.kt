package com.fitnesshub.bial_flyeasy.repositories

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.models.Profile
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants

class ProfileRepository(var apiServices: ApiServices) {
    @JvmField
    val statusLiveData = MutableLiveData<String>()
    val response: LiveData<String>
        get() = statusLiveData


    fun updateProfile(profile: Profile){
        apiServices.updateProfile(profile)
        statusLiveData.postValue(Constants.IN_PROGRESS.toString())
    }
}