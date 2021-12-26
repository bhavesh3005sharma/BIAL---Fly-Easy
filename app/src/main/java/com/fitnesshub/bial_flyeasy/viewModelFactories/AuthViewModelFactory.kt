package com.fitnesshub.bial_flyeasy.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitnesshub.bial_flyeasy.repositories.AuthRepository
import com.fitnesshub.bial_flyeasy.viewModels.AuthViewModel

class AuthViewModelFactory(var authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val authViewModel = AuthViewModel(authRepository)
        return authViewModel as T
    }
}