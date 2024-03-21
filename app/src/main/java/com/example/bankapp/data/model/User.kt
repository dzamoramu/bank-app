package com.example.bankapp.data.model

data class User(
    val userId: String,
    val name: String,
    val lastName: String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "userId" to this.userId,
            "name" to this.name,
            "lastName" to this.lastName
        )
    }
}
