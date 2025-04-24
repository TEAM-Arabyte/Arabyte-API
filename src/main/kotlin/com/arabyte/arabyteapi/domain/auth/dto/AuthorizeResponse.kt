package com.arabyte.arabyteapi.domain.auth.dto

import com.arabyte.arabyteapi.domain.user.entity.User

data class AuthorizeResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean = false,
) {
    companion object {
        fun of(
            user: User,
            accessToken: String,
            refreshToken: String,
            isRegistered: Boolean
        ): AuthorizeResponse {
            return AuthorizeResponse(
                userId = user.id,
                accessToken = accessToken,
                refreshToken = refreshToken,
                isRegistered = isRegistered
            )
        }
    }
}