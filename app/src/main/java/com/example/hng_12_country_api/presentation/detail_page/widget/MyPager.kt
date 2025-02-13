package com.example.hng_12_country_api.presentation.detail_page.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.launch

@Composable
fun MyPager(banners: List<String>, modifier: Modifier) {
    val pagerState = rememberPagerState(pageCount = { Int.MAX_VALUE }) // Infinite scrolling
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Left Arrow (Now on top using zIndex)
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
                PagerItemView(banners[page % banners.size]) // Infinite looping
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

        // Page Indicators (Overlay Inside Pager)
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
fun PagerItemView(image: String?) {
    if (image == null) {
        // Show a placeholder if the image URL is null
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .aspectRatio(16/9f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        ) {
            Text(
                text = "Image not available",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    } else {
        val imageState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(Size.ORIGINAL)
                .build()
        ).state

        when (imageState) {
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                ) {
                    Text(
                        text = "Failed to load image",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            }
            is AsyncImagePainter.State.Success -> {
                Image(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    painter = imageState.painter,
                    contentDescription = "banner"
                )
            }
            else -> {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }

            }
        }
    }
}