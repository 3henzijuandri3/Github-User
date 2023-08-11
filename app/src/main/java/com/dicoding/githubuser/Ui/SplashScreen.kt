@file:Suppress("DEPRECATION")

package com.dicoding.githubuser.Ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.Database.SettingPreferences
import com.dicoding.githubuser.Helper.SettingViewModelFactory
import com.dicoding.githubuser.R
import com.dicoding.githubuser.ViewModels.SettingViewModel
import com.dicoding.githubuser.databinding.ActivitySplashScreenBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var topAnimation : Animation
    private lateinit var topAnimation2 : Animation
    private lateinit var topAnimation3 : Animation
    private lateinit var topAnimation4 : Animation
    private lateinit var bottomAnimation : Animation
    private lateinit var blinkAnimation : Animation

    private val SPLASH_SCREEN_LENGTH : Long = 3200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this){ isDarkModeActive ->

            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }

            animationPLay()
            moveNext()

        }
    }

    private fun animationPLay(){
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_movement)
        topAnimation2 = AnimationUtils.loadAnimation(this, R.anim.top_animation_2)
        topAnimation3 = AnimationUtils.loadAnimation(this, R.anim.top_animation_3)
        topAnimation4 = AnimationUtils.loadAnimation(this, R.anim.top_animation_4)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_movement)
        blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)

        binding.github.startAnimation(bottomAnimation)
        binding.letterU.startAnimation(topAnimation)
        binding.letterS.startAnimation(topAnimation2)
        binding.letterE.startAnimation(topAnimation3)
        binding.letterR.startAnimation(topAnimation4)
        binding.logo.startAnimation(blinkAnimation)

    }

    private fun moveNext(){
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_LENGTH)
    }

    override fun onDestroy() {
        topAnimation.cancel()
        topAnimation2.cancel()
        topAnimation3.cancel()
        topAnimation4.cancel()
        bottomAnimation.cancel()
        blinkAnimation.cancel()
        super.onDestroy()
    }
}