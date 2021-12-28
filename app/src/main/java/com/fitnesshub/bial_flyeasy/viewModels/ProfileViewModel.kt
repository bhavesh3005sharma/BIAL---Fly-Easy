package com.fitnesshub.bial_flyeasy.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.models.Profile
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository):ViewModel(){
    private val toast = MutableLiveData<String>()
    private var userModel:UserModel?=null

    val response: LiveData<ResourceResponse<UserModel>>
        get() = profileRepository.response

    fun validateData(userModel: UserModel) {
        val error: String =
                if (userModel.name?.isEmpty() == true) "Name is Required."
                else if (userModel.address?.isEmpty() == true) "Address is Required."
                else if (userModel.aadharcard_no?.isEmpty() == true) "Aadhar Card Number is Required."
                else if (userModel.phone_no?.isEmpty() == true) "Phone Number is Required."
                else "Correct"
        if (error == "Correct") {
            this.userModel=userModel
            profileRepository.updateProfile(userModel)
        } else toast.setValue(error)
    }

    fun displayToastMsg(): LiveData<String> {
        return toast
    }
}