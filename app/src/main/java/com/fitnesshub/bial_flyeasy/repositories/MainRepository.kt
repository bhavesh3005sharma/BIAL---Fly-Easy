package com.fitnesshub.bial_flyeasy.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.database.Prefs
import com.fitnesshub.bial_flyeasy.models.AuthModel
import com.fitnesshub.bial_flyeasy.models.HomeModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepository @Inject constructor(var apiServices: ApiServices,var prefs: Prefs) {

    @JvmField
    val statusLiveData = MediatorLiveData<ResourceResponse<HomeModel>>()
    private val loadingStatus = MutableLiveData<ResourceResponse<HomeModel>>()
    val response: LiveData<ResourceResponse<HomeModel>>
        get() = statusLiveData

    init {
        statusLiveData.addSource(loadingStatus) { statusLiveData.value = loadingStatus.value }
    }

    fun getData(id:String,airport:String){
        loadingStatus.value = ResourceResponse(Constants.IN_PROGRESS, null, null)
        val source = LiveDataReactiveStreams.fromPublisher(apiServices.getData(id,airport)
            .onErrorReturn { t: Throwable? ->
                val errorUser = ResourceResponse<HomeModel>(Constants.ERROR, null, t?.message)
                errorUser
            }
            .subscribeOn(Schedulers.io()))
        statusLiveData.addSource(source) { statusLiveData.value = it }
    }

}