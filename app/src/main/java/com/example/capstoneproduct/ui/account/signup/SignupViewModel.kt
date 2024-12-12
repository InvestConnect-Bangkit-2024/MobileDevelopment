package com.example.capstoneproduct.ui.account.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.repository.UserRepository
import com.example.capstoneproduct.data.response.register.ResponseRegister

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerResponse = MutableLiveData<ResponseRegister>()
    val registerResponse: MutableLiveData<ResponseRegister> get() = _registerResponse

    suspend fun register(fullname: String, name: String, email: String, password: String) {
        userRepository.register(fullname, name, email, password)
    }

}