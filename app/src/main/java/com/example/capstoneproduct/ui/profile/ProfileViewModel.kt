package com.example.capstoneproduct.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproduct.data.api.ApiService
import com.example.capstoneproduct.data.response.ProfileResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val apiService: ApiService) : ViewModel() {

    private val _profileData = MutableLiveData<ProfileResponse>()
    val profileData: LiveData<ProfileResponse> get() = _profileData

    fun fetchUser Profile(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getUser Profile(userId) // Make sure to implement this method in your ApiService
                if (response.isSuccessful) {
                    _profileData.value = response.body()
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}