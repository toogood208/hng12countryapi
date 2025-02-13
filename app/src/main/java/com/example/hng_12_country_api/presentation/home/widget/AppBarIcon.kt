package com.example.hng_12_country_api.presentation.home.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppBarIcon(isDarkTheme: Boolean, onClick: () -> Unit) {
    Surface(
        color = if(isDarkTheme) Color(0xFF98A2B3).copy(alpha = 0.2f) else Color(0xFFFCFCFD),
        shape = CircleShape,
        modifier = Modifier
            .padding(end = 10.dp)
            .size(32.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize() // Makes the IconButton fill the Surface
        ) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
                contentDescription = "Theme Toggle",
                tint = if (isDarkTheme) Color.White else Color(0xFF1C1917),
                modifier = Modifier.size(24.dp) // Icon size
            )
        }
    }

}