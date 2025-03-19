package com.arabyte.arabyteapi.global.configuration

import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity

@Configuration
class SwaggerConfiguration {
    @Bean
    fun operationCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            val swaggerCustomException =
                handlerMethod.getMethodAnnotation(SwaggerCustomException::class.java)

            if (swaggerCustomException != null) {
                generateCustomExceptionExample(operation, swaggerCustomException)
            }

            operation
        }
    }

    private fun generateCustomExceptionExample(
        operation: Operation,
        swaggerCustomException: SwaggerCustomException
    ) {
        val responses = operation.responses
        val customExceptionGroup = swaggerCustomException.value
        val customExceptions = customExceptionGroup.value

        val customExceptionMap = customExceptions.groupBy { it.status }

        customExceptionMap.forEach { (status, customException) ->
            val content = Content()
            val mediaType = MediaType()
            val apiResponse = ApiResponse()

            customException.forEach {
                mediaType.addExamples(
                    it.name,
                    Example().apply {
                        value = ResponseEntity.status(status).body(it.message)
                    }
                )
            }

            content.addMediaType("application/json", mediaType)
            apiResponse.content = content
            responses.addApiResponse(status.value().toString(), apiResponse)
        }
    }
}