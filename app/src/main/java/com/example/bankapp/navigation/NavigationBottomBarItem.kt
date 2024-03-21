package com.example.bankapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavigationBottomBarItem(var route: String, val icon: ImageVector?, var title: String) {
    object Home : NavigationBottomBarItem(Destination.Home.route, Icons.Rounded.Home, "Home")
    object Profile : NavigationBottomBarItem(Destination.Profile.route, Icons.Rounded.Info, "Profile")
}