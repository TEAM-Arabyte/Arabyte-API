package com.arabyte.arabyteapi.domain.user.service

import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import com.arabyte.arabyteapi.global.enums.CustomError
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

    fun getUser(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { CustomException(CustomError.USER_NOT_FOUND) }
    }

    fun deleteUserById(userId: Long) {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=$userId") }

        userRepository.delete(user)
    }
}