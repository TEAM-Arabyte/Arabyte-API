package com.arabyte.arabyteapi.domain.auth.dto

sealed class AuthorizeResponse

data class LoginResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
) : AuthorizeResponse()

data class RegisterResponse(
    val isRegistered: Boolean = false,
    val userId: Long
) : AuthorizeResponse()