package com.fitnesshub.bial_flyeasy.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitnesshub.bial_flyeasy.repositories.ProfileRepository
import com.fitnesshub.bial_flyeasy.viewModels.ProfileViewModel

class ProfileViewModelFactory(var profileRepository: ProfileRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val profileViewModel=ProfileViewModel(profileRepository)
        return profileViewModel as T
    }
}