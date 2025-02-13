package com.example.hng_12_country_api.presentation.home.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.hng_12_country_api.data.model.CountryItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryListScreen(countryList: List<CountryItem>, isLoading: Boolean, isDarkTheme: Boolean, navController: NavHostController) {

    when{
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }
        countryList.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No data available",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }else ->{
        val groupedListCountry = countryList
            .sortedBy { it.name.common }.groupBy { it.name.common.first().uppercaseChar() }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            groupedListCountry.forEach { (letter, countryList) ->
                stickyHeader {
                    Text(
                        text = letter.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        color = Color(0XFF667085)
                    )
                }
                items(countryList) { country ->
                    CountryItem(
                        country,
                        isDarkTheme = isDarkTheme,
                        navController = navController
                    )
                }
            }
        }

    }
    }


}

@Composable
fun CountryItem(countryItem: CountryItem,  isDarkTheme: Boolean, navController: NavHostController) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(countryItem.flags.png).size(Size.ORIGINAL).build()
    ).state
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("countryDetail/${countryItem.name.common}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(imageState is AsyncImagePainter.State.Error)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            )
        if(imageState is AsyncImagePainter.State.Success)
            Image(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                painter = imageState.painter,
                contentDescription = countryItem.name.common)


        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = countryItem.name.common,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = if(isDarkTheme) Color(0XFFF2F4F7) else Color(0XFF1c1917)
            )
            if (!countryItem.capital.isNullOrEmpty()) {
                Text(text = countryItem.capital.first(), fontSize = 14.sp, color = if(isDarkTheme) Color(0XFF98A2B3) else Color(0XFF667085))
            }
        }
    }
}