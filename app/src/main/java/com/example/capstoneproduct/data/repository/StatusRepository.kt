package com.example.capstoneproduct.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.capstoneproduct.data.response.request.DataItem
import com.example.capstoneproduct.data.retrofit.ApiService


class StatusRepository private constructor(private val apiService: ApiService,
) {

    fun getStatus(): LiveData<Result<List<DataItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getRequest()
            emit(Result.Success(response.data))
            if (response.error == false) {
            } else {
                emit(Result.Error(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }


    companion object {
        @Volatile
        private var instance: StatusRepository? = null

        fun getInstance(apiService: ApiService): StatusRepository =
            instance ?: synchronized(this) {
                instance ?: StatusRepository(apiService)
            }
    }
}