package com.arabyte.arabyteapi.global.exception

import com.arabyte.arabyteapi.global.enums.CustomError
import org.springframework.web.server.ResponseStatusException

class CustomException(
    val customError: CustomError
) :
    ResponseStatusException(customError.status, customError.message)