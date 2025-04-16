package com.arabyte.arabyteapi.domain.auth.dto

data class AuthorizeResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean = false,
)