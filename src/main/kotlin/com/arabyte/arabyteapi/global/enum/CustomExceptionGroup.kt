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

    // article
    ARTICLE_CREATE(listOf(USER_NOT_FOUND)),
    ARTICLE_LIST(listOf()),

    ARTICLE_DETAIL(
        listOf(
            ARTICLE_NOT_FOUND,
            USER_NOT_FOUND
        )
    ),

    ARTICLE_UPDATE(
        listOf(
            ARTICLE_NOT_FOUND,
            ARTICLE_FORBIDDEN
        )
    ),

    ARTICLE_DELETE(
        listOf(
            ARTICLE_NOT_FOUND,
            ARTICLE_FORBIDDEN
        )
    ),

    // comment
    COMMENT_CREATE(
        listOf(
            ARTICLE_NOT_FOUND,
            USER_NOT_FOUND,
            PARENT_COMMENT_NOT_FOUND
        )
    ),

    COMMENT_UPDATE(
        listOf(
            COMMENT_NOT_FOUND,
            COMMENT_FORBIDDEN
        )
    ),

    COMMENT_DELETE(
        listOf(
            COMMENT_NOT_FOUND,
            COMMENT_FORBIDDEN
        )
    ),

    // articleLike
    ARTICLE_LIKE(
        listOf(
            ARTICLE_NOT_FOUND
        )
    ),

}