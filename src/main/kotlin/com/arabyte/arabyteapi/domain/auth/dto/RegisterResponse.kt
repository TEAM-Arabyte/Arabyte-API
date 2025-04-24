package com.arabyte.arabyteapi.domain.auth.dto

import com.arabyte.arabyteapi.domain.user.entity.User

data class RegisterResponse(
    val isRegistered: Boolean,
    val userId: Long,
) {
    companion object {
        fun of(isRegistered: Boolean, user: User): RegisterResponse {
            return RegisterResponse(
                isRegistered = isRegistered,
                userId = user.id
            )
        }
    }
}