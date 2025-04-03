package com.arabyte.arabyteapi.domain.user.service

import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import com.arabyte.arabyteapi.global.exception.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUserByKakaoId(id: Long): User? {
        return userRepository.findByKakaoId(id)
    }

    fun saveUser(newUser: User): User {
        return userRepository.save(newUser)
    }

    fun getUserOrThrow(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { CustomException(CustomError.USER_NOT_FOUND) }
    }
}