package com.fitnesshub.bial_flyeasy.viewModelFactories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fitnesshub.bial_flyeasy.repositories.AuthRepository;
import com.fitnesshub.bial_flyeasy.viewModels.AuthViewModel;

public class AuthViewModelFactory implements ViewModelProvider.Factory {
    AuthRepository authRepository;

    public AuthViewModelFactory(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        AuthViewModel authViewModel = new AuthViewModel(authRepository);
        return (T) authViewModel;
    }
}
