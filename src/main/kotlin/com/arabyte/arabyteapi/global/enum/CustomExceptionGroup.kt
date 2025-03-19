package com.arabyte.arabyteapi.global.enum

import com.arabyte.arabyteapi.global.exception.CustomError
import com.arabyte.arabyteapi.global.exception.CustomError.*

enum class CustomExceptionGroup(
    val value: List<CustomError>
) {
    // auth
    KAKAO_LOGIN(
        listOf(
            GET_KAKAO_ACCESS_TOKEN_FAILED,
            GET_KAKAO_USER_INFO_FAILED
        )
    ),
    LOGIN_OR_REGISTER(
        listOf()
    ),
    REFRESH_ACCESS_TOKEN(
        listOf(
            INVALID_TOKEN
        )
    ),
}