package com.dicoding.githubuser.ViewModels

import android.app.Application
import com.dicoding.githubuser.ApiSettings.ApiConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.Ui.DetailActivity
import com.dicoding.githubuser.ApiSettings.DetailUserResponse
import com.dicoding.githubuser.Database.FavoriteUser
import com.dicoding.githubuser.Repository.FavUserRepository
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser : LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val mFavUserRepository : FavUserRepository = FavUserRepository(application)

    init {
        DetailActivity.USERNAME?.let { fetchUserDetail(it) }
    }

    private fun fetchUserDetail(username : String){

        _isLoading.value = true
        val client = ApiConfig.getApiService().detailUser(username)

        client.enqueue(object : retrofit2.Callback<DetailUserResponse>{

            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {

                if(response.isSuccessful){
                    _detailUser.value = response.body()
                    _isLoading.value = false

                } else {
                    Log.e(TAG, "onFailure: ${response.message()} 111111111111111")
                }

            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message} 22222222222")
                _isLoading.value = false
            }

        })
    }

    fun insert(favUser : FavoriteUser) = mFavUserRepository.insert(favUser)

    fun update(favUser : FavoriteUser) = mFavUserRepository.update(favUser)

    fun delete(favUser : FavoriteUser) = mFavUserRepository.delete(favUser)

    fun getFavoriteUserByUsername(username: String) = mFavUserRepository.getFavoriteUserByUsername(username)

}























