package com.example.capstoneproduct.data.repository

import com.example.capstoneproduct.data.pref.UserModel
import com.example.capstoneproduct.data.pref.UserPreference
import com.example.capstoneproduct.data.response.login.ResponseLogin
import com.example.capstoneproduct.data.response.register.ResponseRegister
import com.example.capstoneproduct.data.retrofit.ApiService
import com.example.capstoneproduct.data.retrofit.AuthApiService

class UserRepository(
    private val userPreference: UserPreference,
    private val authApiService: AuthApiService,
    ) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun register(fullname: String, username: String, email: String, password: String): ResponseRegister {
        return authApiService.register(fullname, username, email, password)
    }

    suspend fun loginInvestor(username: String, email: String): ResponseLogin {
        return authApiService.loginInvestor(username, email)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            authApiService: AuthApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this){
                instance ?: UserRepository(userPreference, authApiService)
            }.also { instance = it }
    }
}