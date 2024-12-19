package com.example.capstoneproduct.ui.status

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.repository.Result
import com.example.capstoneproduct.data.repository.StatusRepository
import com.example.capstoneproduct.data.response.request.DataItem


class StatusViewModel(private val repository: StatusRepository) : ViewModel() {

    private val _statusOffer = MutableLiveData<List<DataItem>>()
    val statusOffer: LiveData<List<DataItem>> get() = _statusOffer

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun getStatus() {
        _isLoading.value = true // Set loading to true initially
        repository.getOffers().observeForever { response ->
            when (response) {
                is Result.Loading -> {
                    _isLoading.value = true
                }
                is Result.Success -> {
                    _statusOffer.value = response.data
                    _isLoading.value = false // Set loading to false when data is loaded
                }
                is Result.Error -> {
                    _isError.value = true
                    _isLoading.value = false // Set loading to false on error
                }
                else -> {
                    Log.e("StatusViewModel", "Unexpected result type")
                    _isLoading.value = false // Ensure loading is false on unexpected result
                }
            }
        }
    }
}