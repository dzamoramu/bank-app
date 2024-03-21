package com.example.bankapp.navigation

sealed class Destination(
    val route: String
) {
    object Login : Destination("screenLogin")
    object Register : Destination("screenRegister")
    object Home : Destination("screenHome")
    object Profile : Destination("screenProfile")
    object Camera : Destination("cameraItem")
    object MovementDetail : Destination("movementItemDetail/{amount}/{concept}/{date}") {
        fun createRoute(amount: String, concept: String, date: String) =
            "movementItemDetail/$amount/$concept/$date"
    }
}
