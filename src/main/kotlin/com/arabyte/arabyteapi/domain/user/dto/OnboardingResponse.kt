package com.arabyte.arabyteapi.domain.user.dto

import com.arabyte.arabyteapi.domain.user.entity.User

data class OnboardingResponse(
    val userId: Long
) {
    companion object {
        fun of(user: User): OnboardingResponse {
            return OnboardingResponse(
                userId = user.id
            )
        }
    }
}