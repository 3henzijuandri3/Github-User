package com.dicoding.githubuser.ViewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.Database.FavoriteUser
import com.dicoding.githubuser.Repository.FavUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavUserRepository : FavUserRepository = FavUserRepository(application)

    fun getAllFavUsers() : LiveData<List<FavoriteUser>> = mFavUserRepository.getAllFavUser()
}