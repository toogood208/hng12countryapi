package com.example.hng_12_country_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.hng_12_country_api.data.api.RetrofitInstance
import com.example.hng_12_country_api.data.repository.CountryRepositoryImpl
import com.example.hng_12_country_api.presentation.navigation.AppNavGraph
import com.example.hng_12_country_api.presentation.viewmodel.CountryViewModel
import com.example.hng_12_country_api.presentation.viewmodel.ThemeViewModel
import com.example.hng_12_country_api.presentation.viewmodel.ThemeViewModelFactory
import com.example.hng_12_country_api.ui.theme.Hng12countryapiTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CountryViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CountryViewModel(
                        countryRepository = CountryRepositoryImpl(
                            RetrofitInstance.api
                        )
                    ) as T
                }
            }
        }

    )

    private val themeViewModel by viewModels<ThemeViewModel> {
        ThemeViewModelFactory(applicationContext)
    }




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            Hng12countryapiTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                AppNavGraph(navController = navController, countryViewModel = viewModel,themeViewModel=themeViewModel)
            }
        }
    }
}

