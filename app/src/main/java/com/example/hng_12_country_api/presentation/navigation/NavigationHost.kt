package com.example.hng_12_country_api.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hng_12_country_api.presentation.detail_page.DetailPage
import com.example.hng_12_country_api.presentation.home.HomeView
import com.example.hng_12_country_api.presentation.viewmodel.CountryViewModel
import com.example.hng_12_country_api.presentation.viewmodel.ThemeViewModel

@Composable
fun AppNavGraph(navController: NavHostController, countryViewModel: CountryViewModel, themeViewModel: ThemeViewModel) {
    NavHost(navController, startDestination = "countryList") {
        composable("countryList") {
            HomeView(countryViewModel = countryViewModel,themeViewModel=themeViewModel,navController)
        }
        composable(
            "countryDetail/{countryName}",
            arguments = listOf(navArgument("countryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: return@composable
            val country = countryViewModel.countries.collectAsState().value.find { it.name.common == countryName }
            country?.let { DetailPage(it,themeViewModel,navController) }

        }
    }
}