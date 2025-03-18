package com.arabyte.arabyteapi.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "카카오 사용자 정보 조회 실패")
class GetKakaoUserInfoFailureException : Exception()