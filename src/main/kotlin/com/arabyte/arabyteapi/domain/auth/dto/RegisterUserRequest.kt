package com.arabyte.arabyteapi.domain.auth.dto

data class RegisterUserRequest(
    val kakaoId: Long,
    val username: String,
    val nickname: String,
    val profileImageUrl: String,
    val ageRange: String,
    val gender: String,
    val email: String,
    val phoneNumber: String?,
    val location: String
)