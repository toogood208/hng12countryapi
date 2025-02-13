package com.example.hng_12_country_api.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hng_12_country_api.presentation.home.widget.AppBarIcon
import com.example.hng_12_country_api.presentation.home.widget.AppBarTitle
import com.example.hng_12_country_api.presentation.home.widget.CountryListScreen
import com.example.hng_12_country_api.presentation.home.widget.CustomSearchBar
import com.example.hng_12_country_api.presentation.viewmodel.CountryViewModel
import com.example.hng_12_country_api.presentation.viewmodel.ThemeViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(countryViewModel: CountryViewModel, themeViewModel: ThemeViewModel, navHostController: NavHostController) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    AppBarTitle(titleName = "Explore")
                },
                actions = {
                    AppBarIcon(isDarkTheme=isDarkTheme, onClick = {themeViewModel.toggleTheme()})
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            HomeBody(countryViewModel,isDarkTheme,navHostController= navHostController)
        }
    }
}

@Composable
fun HomeBody(viewModel: CountryViewModel, isDarkTheme: Boolean,navHostController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    val countryList by remember { viewModel.countries }.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val sortedCountries = countryList
        .filter{it.name.common.contains(searchQuery, ignoreCase = true)}
        .sortedBy { it.name.common }

    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomSearchBar(query = searchQuery, onQueryChange = {searchQuery = it}, isDarkTheme=isDarkTheme)
        CountryListScreen(sortedCountries,isLoading,isDarkTheme, navController = navHostController)
    }
}







