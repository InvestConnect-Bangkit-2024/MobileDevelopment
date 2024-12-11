package com.example.capstoneproduct.ui.investor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.investors.DataItem
import com.example.capstoneproduct.data.repository.InvestorRepository
import com.example.capstoneproduct.data.repository.Result

class InvestorViewModel(
    private val investorRepository: InvestorRepository
) : ViewModel() {

    private val _investor = MutableLiveData<List<DataItem?>?>()
    val investor: MutableLiveData<List<DataItem?>?> get() = _investor

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun getInvestor() {
        investorRepository.getInvestors().observeForever { response ->
            _isLoading.value = false
            when (response) {
                is Result.Loading -> {
                    _isLoading.value = true
                }

                is Result.Success -> {
                    _investor.value = response.data
                    Log.e("InvestorViewModel", response.data.toString())
                }

                is Result.Error -> {
                    _isError.value = true
                    Log.e("InvestorViewModel", response.error)
                }
                else -> {
                    Log.e("InvestorViewModel", "Unexpected result type")
                }
            }
        }
    }
}