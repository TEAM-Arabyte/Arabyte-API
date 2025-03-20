package com.arabyte.arabyteapi.global.annotation

import com.arabyte.arabyteapi.global.enum.CustomExceptionGroup

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SwaggerCustomException(
    val value: CustomExceptionGroup
)
