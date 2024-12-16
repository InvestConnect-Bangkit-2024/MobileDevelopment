package com.example.capstoneproduct.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.capstoneproduct.data.repository.Result
import com.example.capstoneproduct.data.response.home.CompaniesItem
import com.example.capstoneproduct.data.retrofit.HomeApiService

class HomeRepository private constructor(
    private val apiService: HomeApiService
) {

    fun getHome(): LiveData<Result<List<CompaniesItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getRecommend()
            emit(Result.Success(response.companies))
            if (response.error == false) {
            } else {
                emit(Result.Error(response.message.toString()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(apiService: HomeApiService): HomeRepository =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(apiService)
            }

    }
}