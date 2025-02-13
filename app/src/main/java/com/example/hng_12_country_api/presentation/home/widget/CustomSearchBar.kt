package com.example.hng_12_country_api.presentation.home.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {


    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)), // 4px corner radius
        colors = TextFieldDefaults.textFieldColors(
            containerColor = if(isDarkTheme) Color(0xFF98A2B3).copy(alpha = 0.2f) else Color(0xFFF2F4F7), // Light gray background
            unfocusedIndicatorColor = Color.Transparent, // Removes the bottom line
            focusedIndicatorColor = Color.Transparent // Removes the bottom line when focused
        ),
        placeholder = {
            Text(
                "Search Country",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        singleLine = true
    )
}