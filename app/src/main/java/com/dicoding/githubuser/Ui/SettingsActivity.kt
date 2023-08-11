package com.dicoding.githubuser.Ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.Database.SettingPreferences
import com.dicoding.githubuser.Helper.SettingViewModelFactory
import com.dicoding.githubuser.R
import com.dicoding.githubuser.ViewModels.SettingViewModel
import com.dicoding.githubuser.databinding.ActivitySettingsBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    private var _activitySettingsBinding : ActivitySettingsBinding ?= null
    private val binding get() = _activitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val switchDark = binding?.switchTheme

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this){ isDarkModeActive ->

            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchDark?.isChecked = true

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchDark?.isChecked = false
            }

        }

        switchDark?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

        // Change Action Bar Title
        supportActionBar?.setTitle(R.string.settings_title)

    }
}
























