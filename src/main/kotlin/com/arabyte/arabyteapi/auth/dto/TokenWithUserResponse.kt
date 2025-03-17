package com.arabyte.arabyteapi.auth.dto

import com.arabyte.arabyteapi.user.entitiy.User

class TokenWithUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)