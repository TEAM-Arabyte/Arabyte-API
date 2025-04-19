package com.arabyte.arabyteapi.global.resolver

import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import com.arabyte.arabyteapi.global.annotation.RequestUser
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class RequestUserArgumentResolver(
    private val userRepository: UserRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(RequestUser::class.java) != null &&
                parameter.parameterType == User::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val userId =
            webRequest.userPrincipal?.name ?: throw CustomException(CustomError.INVALID_TOKEN)
        val user = userRepository.findById(userId.toLong())
            .orElseThrow { CustomException(CustomError.USER_NOT_FOUND) }

        return user
    }
}