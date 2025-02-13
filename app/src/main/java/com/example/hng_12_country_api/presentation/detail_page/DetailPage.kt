package com.example.hng_12_country_api.presentation.detail_page

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.hng_12_country_api.data.model.CountryItem
import com.example.hng_12_country_api.presentation.detail_page.widget.PagerItemView
import com.example.hng_12_country_api.presentation.home.widget.AppBarTitle
import com.example.hng_12_country_api.presentation.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(
    country: CountryItem,
    themeViewModel: ThemeViewModel,
    navController: NavHostController
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val banners = listOf(
        country.flags.png,
        country.coatOfArms.png,
    )


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    AppBarTitle(titleName = country.name.common, showDot = false)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
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

            if (isLandscape) {
                Row(modifier = Modifier.fillMaxSize()) {
                    // Pager takes up half the screen
                    Box(modifier = Modifier.weight(1f)) {
                        MyPager(banners = banners, modifier = Modifier.fillMaxWidth())
                    }

                    Spacer(modifier = Modifier.width(24.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        DetailsCard(country, isDarkTheme)
                    }
                }
            } else {
                // For portrait, display pager on top and details below
                Column(modifier = Modifier.fillMaxSize()) {
                    MyPager(banners = banners, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailsCard(country, isDarkTheme)
                }
            }
        }
    }
}

@Composable
fun MyPager(banners: List<String>, modifier: Modifier) {
    val pagerState = rememberPagerState(pageCount = { Int.MAX_VALUE }) // Infinite scrolling
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        val prevPage = (pagerState.currentPage - 1 + banners.size) % banners.size
                        pagerState.animateScrollToPage(prevPage)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
                    .background(Color(0XFFF2F4F7).copy(0.3f), shape = CircleShape)
                    .zIndex(2f) // Ensures it stays on top
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Previous", tint = Color(0XFFFCFCFD)
                )
            }

            HorizontalPager(
                state = pagerState,
                pageSpacing = 15.dp,
            ) { page ->
                PagerItemView(banners[page % banners.size])
            }

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        val nextPage = (pagerState.currentPage + 1) % banners.size
                        pagerState.animateScrollToPage(nextPage)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .background(Color(0XFFF2F4F7).copy(0.3f), shape = CircleShape)
                    .zIndex(2f) // Ensures it stays on top
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Next", tint = Color.White)
            }
        }


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage % banners.size == index) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage % banners.size == index) Color.White else Color.Gray)
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DetailsCard(countryDetail: CountryItem, isDarkTheme: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()) // Make it scrollable
    ) {
        val formattedPopulation = NumberFormat.getNumberInstance(Locale.ROOT).format(countryDetail.population)

        DetailCardItem(title = "Name", subTitle = countryDetail.name.common, isDarkTheme)
        DetailCardItem(title = "Official Name", subTitle = countryDetail.name.official, isDarkTheme)
        DetailCardItem(title = "Independent", subTitle = countryDetail.independent.toString(), isDarkTheme)
        DetailCardItem(title = "UN Member", subTitle = countryDetail.unMember.toString(), isDarkTheme)
        DetailCardItem(title = "Car Drive", subTitle = countryDetail.car.side, isDarkTheme)
        DetailCardItem(title = "Land Locked", subTitle = countryDetail.landlocked.toString(), isDarkTheme)
        DetailCardItem(title = "Region", subTitle = countryDetail.region, isDarkTheme)
        DetailCardItem(title = "Sub Region", subTitle = countryDetail.subregion, isDarkTheme)
        DetailCardItem(title = "Population", subTitle = formattedPopulation, isDarkTheme)

        countryDetail.capital?.let {
            DetailCardItem(title = "Capital City", subTitle = it.first(), isDarkTheme)
        }
        DetailCardItem(title = "Continent", subTitle = countryDetail.continents.first(), isDarkTheme)

        countryDetail.postalCode?.let {
            DetailCardItem(title = "Country Code", subTitle = it.format, isDarkTheme)
        }
    }
}

@Composable
fun DetailCardItem(title:String,subTitle:String,isDarkTheme: Boolean){
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        Text("$title:", style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = if(isDarkTheme) Color(0XFFF2F4F7) else Color(0XFF1C1917)
        ))
        Spacer(Modifier.width(8.dp))
        Text(subTitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W300,
                color = if(isDarkTheme) Color(0XFFF2F4F7) else Color(0XFF1C1917)
            ))
    }

}

