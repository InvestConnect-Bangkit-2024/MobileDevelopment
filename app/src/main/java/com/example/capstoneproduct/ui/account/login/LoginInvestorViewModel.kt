package com.example.capstoneproduct.ui.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproduct.data.pref.UserModel
import com.example.capstoneproduct.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginInvestorViewModel(private val repository: UserRepository) : ViewModel() {

    suspend fun login(username: String, password: String) = repository.loginInvestor(username, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}