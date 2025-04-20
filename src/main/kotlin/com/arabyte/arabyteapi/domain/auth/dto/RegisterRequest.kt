package com.arabyte.arabyteapi.domain.auth.dto

data class RegisterRequest(
    val nickname: String,
    val ageRange: String,
    val gender: String,
    val locationId: Long
)