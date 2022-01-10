package com.fitnesshub.bial_flyeasy.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.models.FoodStoreModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.ResourceResponseHome
import com.fitnesshub.bial_flyeasy.repositories.FoodStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodStoreViewModel @Inject constructor(private val foodStoreRepository: FoodStoreRepository):ViewModel(){
    private val toast = MutableLiveData<String>()
    private val response: MediatorLiveData<ResourceResponse<List<FoodStoreModel>>> = MediatorLiveData()

    init {
        response.addSource(foodStoreRepository.response) { response.value = foodStoreRepository.response.value }
    }

    fun displayToastMsg(): LiveData<String> {
        return toast
    }

    fun getResponse(): LiveData<ResourceResponse<List<FoodStoreModel>>> = response

    fun getShopsList(type:String){
        foodStoreRepository.getShopsList(type);
    }
}