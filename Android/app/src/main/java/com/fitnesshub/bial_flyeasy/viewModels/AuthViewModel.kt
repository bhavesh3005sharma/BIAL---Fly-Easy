package com.fitnesshub.bial_flyeasy.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitnesshub.bial_flyeasy.models.ResourceResponse
import com.fitnesshub.bial_flyeasy.models.UserModel
import com.fitnesshub.bial_flyeasy.repositories.AuthRepository
import com.fitnesshub.bial_flyeasy.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val toast = MutableLiveData<String>()
    private var email: String? = null
    private var password: String? = null
    private val response: MediatorLiveData<ResourceResponse<UserModel>> = MediatorLiveData()
    private val alertDialogueStatus: MutableLiveData<Int> = MutableLiveData()

    init {
        response.addSource(authRepository.response) { response.value = authRepository.response.value }
        response.addSource(alertDialogueStatus) { response.value = ResourceResponse(it, null, null) }
    }

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

    fun dismissDialogue() {
        alertDialogueStatus.value = Constants.DISMISS_DIALOGUE
    }

    fun getResponse(): LiveData<ResourceResponse<UserModel>> = response

    fun isUserLoggedIn(): Boolean = authRepository.userLoggedIn

    fun getUser() = authRepository.getUser()
}