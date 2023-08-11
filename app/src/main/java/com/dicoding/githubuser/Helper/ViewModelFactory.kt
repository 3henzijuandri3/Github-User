package com.dicoding.githubuser.Helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.ViewModels.*

class ViewModelFactory private constructor(private val mApplication : Application) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)){
            return FavoriteUserViewModel(mApplication) as T

        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mApplication) as T

        } else if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T

        } else if(modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(mApplication) as T

        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

    }

    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory ?= null

        @JvmStatic
        fun getInstance(application: Application) : ViewModelFactory {

            if(INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(application)
                }
            }

            return INSTANCE as ViewModelFactory
        }
    }

}




































