package com.arabyte.arabyteapi.domain.article.service

import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import com.arabyte.arabyteapi.global.exception.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUserOrThrow(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { CustomException(CustomError.USER_NOT_FOUND) }
    }
}