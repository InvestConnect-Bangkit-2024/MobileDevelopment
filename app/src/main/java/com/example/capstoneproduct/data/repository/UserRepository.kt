package com.example.capstoneproduct.data.repository

import androidx.lifecycle.liveData
import com.example.capstoneproduct.data.investors.RegisterInvestor
import com.example.capstoneproduct.data.pref.UserModel
import com.example.capstoneproduct.data.pref.UserPreference
import com.example.capstoneproduct.data.investors.ResponseLogin
import com.example.capstoneproduct.data.response.register.ResponseRegister
import com.example.capstoneproduct.data.retrofit.AuthApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class UserRepository (
    private val userPreference: UserPreference,
    private val authApiService: AuthApiService
    ) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun registerInvestor(
        imgFile: File,
        username: String,
        email: String,
        password: String,
        fullname: String,
        phone_number: String,
        investor_name: String,
        location: String,
        investment_focus: String,
        stages: String,
        thesis: String,
        total_deals: Int,
        total_investments: Int,
        deal_type: String,
        geographic_focus: String,
        criteria: String
    ) = liveData {
        emit(Result.Loading)

        val usernameRequestBody = username.toRequestBody("text/plain".toMediaType())
        val emailRequestBody = email.toRequestBody("text/plain".toMediaType())
        val passwordRequestBody = password.toRequestBody("text/plain".toMediaType())
        val fullNameRequestBody = fullname.toRequestBody("text/plain".toMediaType())
        val phoneNumberRequestBody = phone_number.toRequestBody("text/plain".toMediaType())
        val investorNameRequestBody = investor_name.toRequestBody("text/plain".toMediaType())
        val locationRequestBody = location.toRequestBody("text/plain".toMediaType())
        val investmentFocusRequestBody = investment_focus.toRequestBody("text/plain".toMediaType())
        val stagesRequestBody = stages.toRequestBody("text/plain".toMediaType())
        val thesisRequestBody = thesis.toRequestBody("text/plain".toMediaType())
        val totalDealsRequestBody = total_deals.toString().toRequestBody("text/plain".toMediaType())
        val totalInvestmentsRequestBody = total_investments.toString().toRequestBody("text/plain".toMediaType())
        val dealTypeRequestBody = deal_type.toRequestBody("text/plain".toMediaType())
        val geographicFocusRequestBody = geographic_focus.toRequestBody("text/plain".toMediaType())
        val criteriaRequestBody = criteria.toRequestBody("text/plain".toMediaType())

        val requestImageFile = imgFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("imgFile", imgFile.name, requestImageFile)

        try {
            val successResponse = authApiService.registerInvestor(
                usernameRequestBody,
                emailRequestBody,
                passwordRequestBody,
                fullNameRequestBody,
                phoneNumberRequestBody,
                investorNameRequestBody,
                locationRequestBody,
                investmentFocusRequestBody,
                stagesRequestBody,
                thesisRequestBody,
                totalDealsRequestBody,
                totalInvestmentsRequestBody,
                dealTypeRequestBody,
                geographicFocusRequestBody,
                criteriaRequestBody,
                multipartBody
            )
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.message
            val errorResponse = Gson().fromJson(errorBody, ResponseRegister::class.java)
            emit(errorResponse?.message?.let { Result.Error(it) })
        }
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