package com.arabyte.arabyteapi.domain.user.dto

data class CheckNickNameResponse(
    val isDuplicate: Boolean,
) {
    companion object {
        fun of(isDuplicate: Boolean): CheckNickNameResponse {
            return CheckNickNameResponse(
                isDuplicate = isDuplicate
            )
        }
    }
}