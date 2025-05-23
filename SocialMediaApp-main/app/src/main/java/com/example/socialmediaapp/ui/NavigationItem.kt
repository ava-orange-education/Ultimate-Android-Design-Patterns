package com.example.socialmediaapp.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)