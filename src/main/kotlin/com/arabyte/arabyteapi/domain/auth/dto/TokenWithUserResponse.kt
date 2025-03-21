package com.arabyte.arabyteapi.domain.auth.dto

import com.arabyte.arabyteapi.domain.user.entity.User

class TokenWithUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)