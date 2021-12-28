package com.fitnesshub.bial_flyeasy.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.models.Profile
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository

class ProfileViewModel (private val profileRepository: ProfileRepository):ViewModel(){
    private val toast = MutableLiveData<String>()
    private var profile=Profile()

    val response: LiveData<String>
        get() = profileRepository.response

    fun validateData(profile: Profile) {
        val error: String =
                if (profile.name.isEmpty()) "Name is Required."
                else if (profile.age.isEmpty()) "Age is Required."
                else if (profile.address.isEmpty()) "Address is Required."
                else if (profile.aadharCard.isEmpty()) "Aadhar Card Number is Required."
                else if (profile.phoneNumber.isEmpty()) "Phone Number is Required."


                else if (profile.phoneNumber.length != 10) "Phone Number should be of 10 digits"
                else if (profile.aadharCard.length != 12) "Aadhar Card Number should be of 12 digits"
                else "Correct"
        if (error == "Correct") {
            this.profile=profile;
            profileRepository.updateProfile(profile)
        } else toast.setValue(error)
    }

    fun displayToastMsg(): LiveData<String> {
        return toast
    }
}