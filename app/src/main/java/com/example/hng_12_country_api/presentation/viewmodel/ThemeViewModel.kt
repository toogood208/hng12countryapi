package com.example.hng_12_country_api.presentation.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("settings")

class ThemeViewModel(context: Context) : ViewModel() {
    private val dataStore = context.dataStore
    private val themeKey = booleanPreferencesKey("dark_theme")

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.data.map { it[themeKey] ?: false }
                .collect { _isDarkTheme.value = it }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            dataStore.edit { settings ->
                val newTheme = !_isDarkTheme.value
                settings[themeKey] = newTheme
                _isDarkTheme.value = newTheme
            }
        }
    }
}
