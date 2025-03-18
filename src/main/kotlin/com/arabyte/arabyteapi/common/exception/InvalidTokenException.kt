package com.arabyte.arabyteapi.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "유효하지 않은 토큰입니다.")
class InvalidTokenException : Exception()