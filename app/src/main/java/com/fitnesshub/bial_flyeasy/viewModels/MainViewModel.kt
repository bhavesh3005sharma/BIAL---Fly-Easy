package com.fitnesshub.bial_flyeasy.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.models.HomeModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.repositories.MainRepository
import com.fitnesshub.bial_flyeasy.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel(){
    private val toast = MutableLiveData<String>()
    private val response: MediatorLiveData<ResourceResponse<HomeModel>> = MediatorLiveData()

    init {
        response.addSource(mainRepository.response) { response.value = mainRepository.response.value }
    }

    fun displayToastMsg(): LiveData<String> {
        return toast
    }

    fun getResponse(): LiveData<ResourceResponse<HomeModel>> = response

    fun getData(id:String,airport:String){
        mainRepository.getData(id,airport);
    }
}