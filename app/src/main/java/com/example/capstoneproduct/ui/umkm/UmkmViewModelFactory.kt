package com.example.capstoneproduct.ui.umkm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproduct.data.di.Injection
import com.example.capstoneproduct.data.repository.UmkmRepository

class UmkmViewModelFactory(private val repository: UmkmRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UmkmViewModel::class.java)) {
            return UmkmViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var INSTANCE: UmkmViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): UmkmViewModelFactory {
            if (INSTANCE == null) {
                synchronized(UmkmViewModelFactory::class.java) {
                    INSTANCE = UmkmViewModelFactory(Injection.provideUmkmRepository(context))
                }
            }
            return INSTANCE as UmkmViewModelFactory
        }
    }
}