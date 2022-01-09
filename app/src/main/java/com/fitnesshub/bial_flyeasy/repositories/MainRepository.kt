package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.database.Preferences
import com.fitnesshub.bial_flyeasy.models.*
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepository @Inject constructor(var apiServices: ApiServices,var prefs: Preferences) {

    @JvmField
    val statusLiveData = MediatorLiveData<ResourceResponseHome>()
    private val loadingStatus = MutableLiveData<ResourceResponseHome>()
    val response: LiveData<ResourceResponseHome>
        get() = statusLiveData

    init {
        statusLiveData.addSource(loadingStatus) { statusLiveData.value = loadingStatus.value }
    }

    fun getData(id:String,airport:String){
        loadingStatus.value = ResourceResponseHome(Constants.IN_PROGRESS, null)
        val source = LiveDataReactiveStreams.fromPublisher(apiServices.getData(HomeRequestModel(id,airport))
            .onErrorReturn { t: Throwable? ->
                val errorUser = ResourceResponseHome(Constants.ERROR, t?.message)
                errorUser
            }
            .subscribeOn(Schedulers.io()))
        statusLiveData.addSource(source) { statusLiveData.value = it }
    }

}