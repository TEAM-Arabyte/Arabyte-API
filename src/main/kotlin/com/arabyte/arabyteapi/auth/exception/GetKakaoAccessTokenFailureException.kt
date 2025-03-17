package com.arabyte.arabyteapi.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "카카오 access token을 가져오는데 실패했습니다.")
class GetKakaoAccessTokenFailureException : Exception()