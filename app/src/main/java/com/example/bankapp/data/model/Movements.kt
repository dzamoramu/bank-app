package com.example.bankapp.data.model

import java.util.Date

data class UserMovement(
    val balance: String = "",
    val movements: List<Movements> = emptyList()
)

data class Movements(
    val date: Date? = null,
    val concept: String = "",
    val amount: String = ""
)