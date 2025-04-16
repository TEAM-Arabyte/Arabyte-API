package com.arabyte.arabyteapi.domain.auth.dto.jwt

data class ReissueAccessTokenRequest(
    val refreshToken: String
)