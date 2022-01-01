package com.fitnesshub.bial_flyeasy.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.database.Prefs
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository,prefs: Prefs):ViewModel(){
    private val toast = MutableLiveData<String>()

    fun validateData(userModel: UserModel): String? {
        val error: String =
                if (userModel.name?.isEmpty() == true) "Name is Required."
                else if (userModel.homeCityAddress?.isEmpty() == true) "Address is Required."
                else if (userModel.aadharNo?.isEmpty() == true) "Aadhar Card Number is Required."
                else if (userModel.phone?.isEmpty() == true) "Phone Number is Required."
                else if(userModel.phone?.length!=10)"Phone Number should be of 10 digits"
                else if(userModel.aadharNo?.length!=12)"Aadhar Card Number should be of 12 digits"
                else if(userModel.age<0 || userModel.age>100)"Age should be in reasonable range"
                else "Correct"
        //toast.setValue(error)
        return error
    }

    fun updateProfile(userModel: UserModel)=profileRepository.updateProfile(userModel)

    fun displayToastMsg(): LiveData<String> {
        return toast
    }

    fun getUserData()=profileRepository.getUserData()

}