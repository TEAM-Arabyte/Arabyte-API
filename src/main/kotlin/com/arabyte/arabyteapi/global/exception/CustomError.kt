package com.arabyte.arabyteapi.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class CustomError(val status: HttpStatus, val message: String) {
    // common
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    // auth
    GET_KAKAO_ACCESS_TOKEN_FAILED(NO_CONTENT, "카카오 액세스 토큰을 가져오는데 실패했습니다."),
    GET_KAKAO_USER_INFO_FAILED(NO_CONTENT, "카카오 사용자 정보를 가져오는데 실패했습니다."),

    // article
    ARTICLE_NOT_FOUND(NOT_FOUND, "해당 게시글이 존재하지 않습니다."),

    // comment
    PARENT_COMMENT_NOT_FOUND(NOT_FOUND, "부모 댓글이 존재하지 않습니다."),

    // user
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
}