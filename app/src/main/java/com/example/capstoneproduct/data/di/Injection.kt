package com.example.capstoneproduct.data.di

import android.content.Context
import com.example.capstoneproduct.data.pref.UserPreference
import com.example.capstoneproduct.data.repository.UserRepository
import com.example.capstoneproduct.data.pref.datastore
import com.example.capstoneproduct.data.repository.DealRepository
import com.example.capstoneproduct.data.repository.InvestorRepository
import com.example.capstoneproduct.data.repository.StatusRepository
import com.example.capstoneproduct.data.repository.UmkmRepository
import com.example.capstoneproduct.data.repository.UploadRepository
import com.example.capstoneproduct.data.response.home.HomeResponse
import com.example.capstoneproduct.data.retrofit.ApiConfig
import com.example.capstoneproduct.ui.home.HomeRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val userPreference = UserPreference.getInstance(context.datastore)
        val authApiService = ApiConfig.getAuthApiService()
        return UserRepository.getInstance(authApiService, userPreference)
    }

    fun provideInvestorRepository(context: Context): InvestorRepository {
        val userToken = UserPreference.getInstance(context.datastore).getSession()
        val token = runBlocking {
            withContext(Dispatchers.IO) {
                userToken.first().token
            }
        }
        val apiService = ApiConfig.getApiService(token)
        return InvestorRepository.getInstance(apiService)
    }

    fun provideHomeRepository(context: Context): HomeRepository {
        val userToken = UserPreference.getInstance(context.datastore).getSession()
        val token = runBlocking {
            withContext(Dispatchers.IO) {
                userToken.first().token
            }
        }
        val apiService = ApiConfig.getHomeApiService()
        return HomeRepository.getInstance(apiService)
    }

    fun provideUmkmRepository(context: Context): UmkmRepository {
        val userToken = UserPreference.getInstance(context.datastore).getSession()
        val token = runBlocking {
            withContext(Dispatchers.IO) {
                userToken.first().token
            }
        }
        val apiService = ApiConfig.getApiService(token)
        val userPreference = UserPreference.getInstance(context.datastore)
        return UmkmRepository.getInstance(apiService, userPreference)
    }

    fun provideUploadRepository(context: Context): UploadRepository {
        val userPreference = UserPreference.getInstance(context.datastore)
        val userToken = UserPreference.getInstance(context.datastore).getSession()
        val token = runBlocking {
            withContext(Dispatchers.IO) {
                userToken.first().token
            }
        }
        val uploadApiService = ApiConfig.getUploadApiService(token)
        return UploadRepository.getInstance(uploadApiService, userPreference)
    }

    fun provideDealRepository(context: Context): DealRepository {
        val userPreference = UserPreference.getInstance(context.datastore)
        val userToken = UserPreference.getInstance(context.datastore).getSession()
        val token = runBlocking {
            withContext(Dispatchers.IO) {
                userToken.first().token
            }
        }
        val dealApiService = ApiConfig.getDealApiService(token)
        return DealRepository.getInstance(dealApiService, userPreference)
    }

    fun provideStatusRepository(context: Context): StatusRepository {
        val userToken = UserPreference.getInstance(context.datastore).getSession()
        val token = runBlocking {
            withContext(Dispatchers.IO) {
                userToken.first().token
            }
        }
        val apiService = ApiConfig.getApiService(token)
        return StatusRepository.getInstance(apiService)
    }
}