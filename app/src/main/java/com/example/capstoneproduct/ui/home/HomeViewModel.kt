package com.example.capstoneproduct.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproduct.data.repository.Result
import com.example.capstoneproduct.data.response.home.CompaniesItem


class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _companiesList = MutableLiveData<List<CompaniesItem>>()
    val companiesList: LiveData<List<CompaniesItem>> get() = _companiesList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun getCompanies() {
        homeRepository.getHome().observeForever{ response ->
            _isLoading.value = true
            when (response) {
                is Result.Loading -> {
                    _isLoading.value = true
                }
                is Result.Success -> {
                    _companiesList.value = response.data
                }
                is Result.Error -> {
                    _isError.value = true
                }
                else -> {
                    Log.e("UmkmViewModel", "Unexpected result type")
                }
            }
        }    }
}