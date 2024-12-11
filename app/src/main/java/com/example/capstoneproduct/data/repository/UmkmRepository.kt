package com.example.capstoneproduct.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.capstoneproduct.data.pref.UserPreference
import com.example.capstoneproduct.data.retrofit.ApiService
import com.example.capstoneproduct.data.umkm.DataItem

class UmkmRepository private constructor(
    private val apiService: ApiService
) {
    fun getUmkm(): LiveData<Result<List<DataItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUmkm()
            emit(Result.Success(response.data))
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
        private var instance: UmkmRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): UmkmRepository =
            instance ?: synchronized(this) {
                instance ?: UmkmRepository(apiService)
            }
    }
}