package com.example.capstoneproduct.ui.umkm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproduct.data.repository.Result
import com.example.capstoneproduct.data.repository.UmkmRepository
import com.example.capstoneproduct.data.retrofit.ApiConfig
import com.example.capstoneproduct.data.umkm.DataItem
import com.example.capstoneproduct.data.umkm.Umkm
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UmkmViewModel(
    private val umkmRepository: UmkmRepository
) : ViewModel() {

    private val _umkm = MutableLiveData<List<DataItem>>()
    val umkm: MutableLiveData<List<DataItem>> get() = _umkm

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun getUmkm() {
        umkmRepository.getUmkm().observeForever{ response ->
            _isLoading.value = true
            when (response) {
                is Result.Loading -> {
                    _isLoading.value = true
                }
                is Result.Success -> {
                    _umkm.value = response.data
                }
                is Result.Error -> {
                    _isError.value = true
                }
                else -> {
                    Log.e("UmkmViewModel", "Unexpected result type")
                }
            }
        }
    }
}
