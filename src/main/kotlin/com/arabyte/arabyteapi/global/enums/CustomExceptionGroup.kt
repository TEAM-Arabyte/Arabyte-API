package com.arabyte.arabyteapi.global.enums

import com.arabyte.arabyteapi.global.enums.CustomError.*

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
    REFRESH_ACCESS_TOKEN(
        listOf(
            INVALID_TOKEN,
            ACCESS_TOKEN_EXPIRED
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