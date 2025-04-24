package com.arabyte.arabyteapi.domain.user.dto

data class DeleteUserResponse(
    val userId: Long,
) {
    companion object {
        fun of(userId: Long): DeleteUserResponse {
            return DeleteUserResponse(
                userId = userId
            )
        }
    }
}