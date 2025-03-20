package com.arabyte.arabyteapi.domain.auth.dto

import com.arabyte.arabyteapi.domain.user.entitiy.User

class TokenWithUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)