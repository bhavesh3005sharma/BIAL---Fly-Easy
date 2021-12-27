package com.fitnesshub.bial_flyeasy.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val toast = MutableLiveData<String>()
    private var email: String? = null
    private var password: String? = null
    val response: LiveData<String>
        get() = authRepository.response

    fun validateData(email: String, password: String) {
        val error: String =
                if (email.isEmpty()) "Email is Required."
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Please Provide Valid Email."
                else if (password.isEmpty()) "Password is Required."
                else if (password.length < 6) "At least 6 character Password is Required."
                else "Correct"
        if (error == "Correct") {
            this.email = email
            this.password = password
            authRepository.signIn(email, password)
        } else toast.setValue(error)
    }

    fun displayToastMsg(): LiveData<String> {
        return toast
    }

    fun signUp() {
        authRepository.signUp(email, password)
    }
}