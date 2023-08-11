package com.dicoding.githubuser.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.Adapter.ListFavoriteUserAdapter
import com.dicoding.githubuser.Database.FavoriteUser
import com.dicoding.githubuser.Helper.ViewModelFactory
import com.dicoding.githubuser.R
import com.dicoding.githubuser.ViewModels.FavoriteUserViewModel
import com.dicoding.githubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private var _activityFavUserBinding : ActivityFavoriteUserBinding ?= null
    private val binding get() = _activityFavUserBinding

    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Pembuatan RecylerView
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvFavUser?.layoutManager = layoutManager
        binding?.rvFavUser?.addItemDecoration(itemDecoration)


        // embuatan View Model
        favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllFavUsers().observe(this){favUserList ->
            if(favUserList != null) {
                setFavUserData(favUserList)
            }
        }

        // Change Action Bar Title
        supportActionBar?.setTitle(R.string.fav_user_title)
    }

    private fun setFavUserData(listFavUser : List<FavoriteUser>){
        val adapter = ListFavoriteUserAdapter(listFavUser)
        binding?.rvFavUser?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavUserBinding = null
    }
}


























