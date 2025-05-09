package com.arabyte.arabyteapi.global.enums

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class CustomError(val status: HttpStatus, val message: String) {
    // common
    ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, "Access Token이 만료되었습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    // auth
    GET_KAKAO_ACCESS_TOKEN_FAILED(NO_CONTENT, "카카오 액세스 토큰을 가져오는데 실패했습니다."),
    GET_KAKAO_USER_INFO_FAILED(NO_CONTENT, "카카오 사용자 정보를 가져오는데 실패했습니다."),

    // article
    ARTICLE_NOT_FOUND(NOT_FOUND, "해당 게시글이 존재하지 않습니다."),
    ARTICLE_FORBIDDEN(FORBIDDEN, "해당 게시물에 대한 권한이 없습니다."),

    // comment
    COMMENT_NOT_FOUND(NOT_FOUND, "해당 댓글이 존재하지 않습니다."),
    COMMENT_FORBIDDEN(FORBIDDEN, "해당 댓글에 대한 권한이 없습니다."),
    PARENT_COMMENT_NOT_FOUND(NOT_FOUND, "부모 댓글이 존재하지 않습니다."),

    // user
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // location
    LOCATION_NOT_FOUND(NOT_FOUND, "위치를 찾을 수 없습니다."),

    // review
    REVIEW_NOT_FOUND(NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    REVIEW_NOT_AUTHORIZED(FORBIDDEN, "리뷰에 대한 권한이 없습니다."),
    CAN_NOT_ADD_HELPFUL_FOR_OWN_REVIEW(CONFLICT, "자신의 리뷰에 대해 추천할 수 없습니다."),

    // ocr
    OCR_REQUEST_FAILED(INTERNAL_SERVER_ERROR, "OCR 서버 요청에 실패했습니다."),
    OCR_RESPONSE_NULL(INTERNAL_SERVER_ERROR, "OCR 서버 응답 파싱에 실패했습니다."),

    // company
    DUPLICATE_COMPANY(CONFLICT, "이미 존재하는 회사입니다."),
    COMPANY_NOT_FOUND(NOT_FOUND, "회사를 찾을 수 없습니다.");
}