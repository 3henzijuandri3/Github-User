package com.dicoding.githubuser.ViewModels

import android.app.Application
import com.dicoding.githubuser.ApiSettings.ApiConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.ApiSettings.UserResponse
import retrofit2.Call
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
        private const val FETCH_LIST_USER = "username"
    }

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse : LiveData<UserResponse> = _userResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        fetchUserGithub()
    }

    private fun fetchUserGithub(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(FETCH_LIST_USER)

        client.enqueue(object : retrofit2.Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {

                if(response.isSuccessful){
                    _isLoading.value = false
                    _userResponse.value = response.body()?.copy()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    fun searchUser(query : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)

        client.enqueue(object : retrofit2.Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {

                if(response.isSuccessful){
                    _isLoading.value = false
                    _userResponse.value = response.body()?.copy()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()} aaaaaaaaaaaaaaaaaaaaaaaa")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

}































