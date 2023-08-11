package com.dicoding.githubuser.Ui

import android.content.Intent
import com.dicoding.githubuser.Adapter.ListUserAdapter
import com.dicoding.githubuser.Adapter.User
import com.dicoding.githubuser.ApiSettings.ItemsItem
import com.dicoding.githubuser.ViewModels.MainViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.Helper.ViewModelFactory
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding : ActivityMainBinding?= null
    private val binding get() = _activityMainBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Pembuatan RecylerView
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvUser?.layoutManager = layoutManager
        binding?.rvUser?.addItemDecoration(itemDecoration)

        // Pembuatan Main View Model
        mainViewModel = obtainViewModel(this@MainActivity)

        mainViewModel.userResponse.observe(this){ userReponse -> setUserData(userReponse.items) }

        mainViewModel.isLoading.observe(this){ showLoading(it) }

        // Search User
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            private var isSearching = false

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) mainViewModel.searchUser(query)
                isSearching = true
                binding?.searchView!!.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty() && isSearching == true) {
                    mainViewModel.searchUser("username")
                    isSearching = false
                    binding?.searchView!!.clearFocus()
                }
                return true
            }

        })

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.favUserMenu -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> return true

        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    private fun setUserData(user : List<ItemsItem>){
        val listUser = getUserGithub(user)
        val adapter = ListUserAdapter(listUser)
        binding?.rvUser?.adapter = adapter
    }

    private fun getUserGithub(userGithub: List<ItemsItem>): ArrayList<User> {
        val listUser = ArrayList<User>()

        for(userData in userGithub){
            val user = User(userData.login, userData.avatarUrl)
            listUser.add(user)
        }

        return listUser
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }
}
























