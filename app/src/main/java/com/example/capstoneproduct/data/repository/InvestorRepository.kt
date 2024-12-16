package com.example.capstoneproduct.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.capstoneproduct.data.investors.DataItem
import com.example.capstoneproduct.data.pref.UserPreference
import com.example.capstoneproduct.data.retrofit.ApiService

class InvestorRepository private constructor(
    private val apiService: ApiService,
) {
    fun getInvestors(): LiveData<Result<List<DataItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getInvestor()
            emit(Result.Success(response.data))

            if (!response.error!!) {
            } else {
                emit(Result.Error(response.message ?: "Unknown error")) // Emit error if response indicates failure
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error")) // Emit error for exceptions
        }
    }



    companion object {
        @Volatile
        private var instance: InvestorRepository? = null

        fun getInstance(apiService: ApiService): InvestorRepository =
            instance ?: synchronized(this) {
                instance ?: InvestorRepository(apiService)
            }
    }

}