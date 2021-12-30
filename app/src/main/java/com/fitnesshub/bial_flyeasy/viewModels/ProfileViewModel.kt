package com.fitnesshub.bial_flyeasy.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.database.Prefs
import com.fitnesshub.bial_flyeasy.database.Prefs.*
import com.fitnesshub.bial_flyeasy.models.Profile
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository):ViewModel(){
    private val toast = MutableLiveData<String>()
    var updateResponse: LiveData<ResourceResponse<Unit>>? = null
    var userData:MediatorLiveData<ResourceResponse<UserModel>> = MediatorLiveData()
    var localUserData:MutableLiveData<ResourceResponse<UserModel>> = MutableLiveData()
    var data: MutableLiveData<ResourceResponse<UserModel>> = MutableLiveData()

    init {
        localUserData.value?.data =Prefs.getUser()
        userData.addSource(localUserData){userData.value=localUserData.value}
        userData.addSource(data){userData.value=data.value}
    }

    val response: LiveData<ResourceResponse<UserModel>>
        get() = profileRepository.response

    fun validateData(userModel: UserModel): LiveData<ResourceResponse<Unit>>? {
        val error: String =
                if (userModel.name?.isEmpty() == true) "Name is Required."
                else if (userModel.address?.isEmpty() == true) "Address is Required."
                else if (userModel.aadharcard_no?.isEmpty() == true) "Aadhar Card Number is Required."
                else if (userModel.phone_no?.isEmpty() == true) "Phone Number is Required."
                else if(userModel.phone_no?.length!=10)"Phone Number should be of 10 digits"
                else if(userModel.aadharcard_no?.length!=12)"Aadhar Card Number should be of 12 digits"
                else if(userModel.age<0 || userModel.age>100)"Age should be in reasonable range"
                else "Correct"
        if (error == "Correct") {
            updateResponse=profileRepository.updateProfile(userModel)
        } else toast.setValue(error)
        return updateResponse
    }


    fun displayToastMsg(): LiveData<String> {
        return toast
    }

    fun getUserData(userModel: UserModel):MediatorLiveData<ResourceResponse<UserModel>>{
        userData=profileRepository.getUserData(userModel)
        return userData
    }

}