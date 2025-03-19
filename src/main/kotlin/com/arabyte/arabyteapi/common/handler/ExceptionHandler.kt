package com.arabyte.arabyteapi.common.handler

import com.arabyte.arabyteapi.common.exception.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun customExceptionHandler(e: CustomException): ResponseEntity<String> {
        return ResponseEntity.status(e.statusCode).body(e.message)
    }
}