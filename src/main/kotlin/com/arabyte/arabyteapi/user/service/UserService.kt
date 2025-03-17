package com.arabyte.arabyteapi.user.service

import com.arabyte.arabyteapi.user.entitiy.User
import com.arabyte.arabyteapi.user.repository.UserRepository
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
}