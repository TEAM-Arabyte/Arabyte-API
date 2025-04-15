package com.arabyte.arabyteapi.domain.auth.dto

data class RegisterResponse(
    val isRegistered: Boolean,
    val userId: Long,
)