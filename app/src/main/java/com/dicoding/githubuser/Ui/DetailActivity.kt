package com.dicoding.githubuser.Ui

import com.dicoding.githubuser.Adapter.SectionsPagerAdapter
import com.dicoding.githubuser.ApiSettings.DetailUserResponse
import com.dicoding.githubuser.ViewModels.DetailViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.Database.FavoriteUser
import com.dicoding.githubuser.Helper.ViewModelFactory
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    companion object {
        var USERNAME : String ?= null

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private var _activityDetailBinding : ActivityDetailBinding ?= null
    private val binding get() = _activityDetailBinding

    private lateinit var detailViewModel : DetailViewModel

    private lateinit var userResponseCopy : DetailUserResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_love)
        binding?.loveUser?.startAnimation(bottomAnimation)

        // Get Username
        val usernameGithub = intent.getStringExtra(USERNAME)
        USERNAME = usernameGithub

        // Membuat Tab Layout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if(usernameGithub != null) sectionsPagerAdapter.username = usernameGithub

        val viewPager : ViewPager2? = binding?.viewPager
        if (viewPager != null) viewPager.adapter = sectionsPagerAdapter


        val tabs : TabLayout ?= binding?.tabs
        if (tabs != null && viewPager != null) {
            TabLayoutMediator(tabs, viewPager) {tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }


        // Membuat Detail View Model
        detailViewModel = obtainViewModel(this@DetailActivity)
        detailViewModel.detailUser.observe(this) { setTvData(it) }
        detailViewModel.isLoading.observe(this) { showLoading(it) }

        val user_name = usernameGithub
        if (user_name != null){

            detailViewModel.getFavoriteUserByUsername(user_name).observe(this){ favUser ->

                if(favUser != null){
                    favUserChecked()

                    binding?.loveUser?.setOnClickListener {
                        detailViewModel.delete(favUser)
                    }


                } else {
                    favUserUnchecked()

                    binding?.loveUser?.setOnClickListener {
                        val name = userResponseCopy.login
                        val url = userResponseCopy.avatarUrl

                        detailViewModel.insert(FavoriteUser(0, name, url))

                    }
                }
            }
        }

        // Change Action Bar Title
        supportActionBar?.setTitle(R.string.detail_title)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }

    private fun favUserChecked() = binding?.loveUser?.setImageResource(R.drawable.ic_fav_clicked)

    private fun favUserUnchecked() = binding?.loveUser?.setImageResource(R.drawable.ic_fav)

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    private fun setTvData(responseDetail : DetailUserResponse){

        userResponseCopy = responseDetail

        binding?.detailActivity?.let {
            binding?.imgDetail?.let { it1 ->
                Glide.with(it)
                    .load(responseDetail.avatarUrl)
                    .into(it1)
            }
        }

        binding?.namaUser?.text = responseDetail.login
        binding?.followerCount?.text = responseDetail.followers.toString()
        binding?.followingCount?.text = responseDetail.following.toString()
        binding?.otherNamaUser?.text = responseDetail.name
        binding?.repoCount?.text = responseDetail.publicRepos.toString()
        binding?.gistCount?.text = responseDetail.publicGists.toString()
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }
}