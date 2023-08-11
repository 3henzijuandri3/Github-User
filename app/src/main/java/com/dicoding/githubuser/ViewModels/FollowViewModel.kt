package com.dicoding.githubuser.ViewModels

import android.app.Application
import com.dicoding.githubuser.ApiSettings.ApiConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.Ui.FollowFragment
import com.dicoding.githubuser.ApiSettings.FollowResponseItem
import retrofit2.Call
import retrofit2.Response

class FollowViewModel(application: Application) : ViewModel() {
    companion object {
        private const val TAG = "FollowViewModel"
    }

    private val _followerResponse = MutableLiveData<List<FollowResponseItem>>()
    val followerResponse : LiveData<List<FollowResponseItem>> = _followerResponse

    private val _followingResponse = MutableLiveData<List<FollowResponseItem>>()
    val followingResponse : LiveData<List<FollowResponseItem>> = _followingResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        FollowFragment.KEY_USERNAME?.let { fetchFollowerGithub(it) }
        FollowFragment.KEY_USERNAME?.let { fetchFollowingGithub(it) }
    }

    private fun fetchFollowerGithub(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)

        if (client != null) {
            client.enqueue(object : retrofit2.Callback<List<FollowResponseItem>>{

                override fun onResponse(
                    call: Call<List<FollowResponseItem>>,
                    response: Response<List<FollowResponseItem>>
                ) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _followerResponse.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()} aaaaaaaaaaaaaaaaaaaaaaaa")
                    }
                }

                override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${t.message}")
                }

            })
        }
    }

    private fun fetchFollowingGithub(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)

        if (client != null) {
            client.enqueue(object : retrofit2.Callback<List<FollowResponseItem>> {

                override fun onResponse(
                    call: Call<List<FollowResponseItem>>,
                    response: Response<List<FollowResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        _followingResponse.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()} aaaaaaaaaaaaaaaaaaaaaaaa")
                    }
                }

                override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure : ${t.message}")
                }

            })
        }
    }
}

