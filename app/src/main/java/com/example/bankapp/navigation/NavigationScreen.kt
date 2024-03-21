package com.example.bankapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bankapp.ui.theme.components.CameraItem
import com.example.bankapp.ui.theme.components.MovementItemDetail
import com.example.bankapp.ui.theme.home.HomeScreen
import com.example.bankapp.ui.theme.login.LoginScreen
import com.example.bankapp.ui.theme.profile.ProfileScreen
import com.example.bankapp.ui.theme.register.RegisterScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationScreen(
    navigationController: NavHostController
) {
    NavHost(navController = navigationController, startDestination = Destination.Login.route) {
        composable(Destination.Login.route) {
            if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
                LaunchedEffect(key1 = Unit) {
                    navigationController.navigate(
                        Destination.Home.route
                    ) {
                        popUpTo(route = Destination.Login.route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                LoginScreen(navController = navigationController)
            }
        }
        composable(Destination.Register.route) { RegisterScreen(navController = navigationController) }
        composable(Destination.Home.route) { HomeScreen(navController = navigationController) }
        composable(Destination.Profile.route) { ProfileScreen(navController = navigationController) }
        composable(
            Destination.MovementDetail.route,
            arguments = listOf(
                navArgument("amount") { type = NavType.StringType },
                navArgument("concept") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.let {
                MovementItemDetail(
                    navController = navigationController,
                    amount = it.getString("amount") ?: "",
                    concept = it.getString("concept") ?: "",
                    date = it.getString("date") ?: ""
                )
            }
        }
        composable(Destination.Camera.route) { CameraItem(navController = navigationController) }
    }
}