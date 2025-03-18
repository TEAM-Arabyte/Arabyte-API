package com.arabyte.arabyteapi.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "사용자가 존재하지 않습니다.")
class UserNotFoundException : Exception()