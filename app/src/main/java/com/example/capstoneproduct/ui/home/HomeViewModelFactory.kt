package com.example.capstoneproduct.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproduct.data.di.Injection


class HomeViewModelFactory(private val repository: HomeRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): HomeViewModelFactory {
            if (INSTANCE == null) {
                synchronized(HomeViewModelFactory::class.java) {
                    INSTANCE = HomeViewModelFactory(Injection.provideHomeRepository(context))
                }
            }
            return INSTANCE as HomeViewModelFactory
        }
    }
}