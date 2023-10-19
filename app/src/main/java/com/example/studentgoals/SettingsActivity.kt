package com.example.studentgoals

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var lightThemeRadioButton: RadioButton
    private lateinit var darkThemeRadioButton: RadioButton
    private lateinit var themeRadioGroup: RadioGroup
    private lateinit var followSystemRadioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE)

        lightThemeRadioButton = findViewById(R.id.lightThemeRadioButton)
        darkThemeRadioButton = findViewById(R.id.darkThemeRadioButton)
        followSystemRadioButton = findViewById(R.id.followSystemRadioButton)

        // Check the saved theme preference and update the UI accordingly
        val currentTheme = sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        updateThemeRadioButton(currentTheme)

        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lightThemeRadioButton -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    saveThemePreference(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.darkThemeRadioButton -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    saveThemePreference(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    saveThemePreference(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    private fun updateThemeRadioButton(theme: Int) {
        when (theme) {
            AppCompatDelegate.MODE_NIGHT_NO -> lightThemeRadioButton.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> darkThemeRadioButton.isChecked = true
            else -> followSystemRadioButton.isChecked = true
        }
    }

    private fun saveThemePreference(theme: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("theme", theme)
        editor.apply()
    }
}

