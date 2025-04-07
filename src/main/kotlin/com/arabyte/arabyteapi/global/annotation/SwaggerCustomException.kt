package com.arabyte.arabyteapi.global.annotation

import com.arabyte.arabyteapi.global.enums.CustomExceptionGroup

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SwaggerCustomException(
    val value: CustomExceptionGroup
)
