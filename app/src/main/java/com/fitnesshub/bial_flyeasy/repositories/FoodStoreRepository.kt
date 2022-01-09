package com.fitnesshub.bial_flyeasy.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fitnesshub.bial_flyeasy.database.Prefs
import com.fitnesshub.bial_flyeasy.models.FoodStoreModel
import com.fitnesshub.bial_flyeasy.models.HomeRequestModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.ResourceResponseHome
import com.fitnesshub.bial_flyeasy.retrofit.ApiServices
import com.fitnesshub.bial_flyeasy.utils.Constants
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FoodStoreRepository @Inject constructor(var apiServices: ApiServices, var prefs: Prefs) {
    @JvmField

    val statusLiveData = MediatorLiveData<ResourceResponse<List<FoodStoreModel>>>()
    private val loadingStatus = MutableLiveData<ResourceResponse<List<FoodStoreModel>>>()
    val response: LiveData<ResourceResponse<List<FoodStoreModel>>>
        get() = statusLiveData

    init {
        statusLiveData.addSource(loadingStatus) { statusLiveData.value = loadingStatus.value }
    }

    fun getShopsList(type:String){
        loadingStatus.value = ResourceResponse<List<FoodStoreModel>>(Constants.IN_PROGRESS, null,null)
        val source = LiveDataReactiveStreams.fromPublisher(apiServices.getShopsList(type)
            .onErrorReturn { t: Throwable? ->
                val errorUser = ResourceResponse<List<FoodStoreModel>>(Constants.ERROR, null,t?.message)
                errorUser
            }
            .subscribeOn(Schedulers.io()))
        statusLiveData.addSource(source) { statusLiveData.value = it }
    }
}