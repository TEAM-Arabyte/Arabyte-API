package com.arabyte.arabyteapi.domain.user.service

import com.arabyte.arabyteapi.domain.user.dto.CheckNickNameResponse
import com.arabyte.arabyteapi.domain.user.dto.OnboardingRequest
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.repository.UserRepository
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.transaction.Transactional
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

    fun getUserByUserId(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { CustomException(CustomError.USER_NOT_FOUND) }
    }

    @Transactional
    fun deleteUserById(userId: Long) {
        val user = userRepository.findById(userId)
            .orElseThrow { CustomException(CustomError.USER_NOT_FOUND) }

        userRepository.delete(user)
    }

    fun isNicknameExists(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    fun checkNickNameDuplicate(nickname: String): CheckNickNameResponse {
        val isDuplicate = isNicknameExists(nickname)
        val message = if (isDuplicate) "닉네임이 중복됩니다." else "사용 가능한 닉네임입니다."

        return CheckNickNameResponse(
            isDuplicate = isDuplicate,
            massage = message
        )
    }

    fun updateOnboarding(request: OnboardingRequest): User {
        val user = getUserByUserId(request.userId)
        user.experienceYears = request.experienceYears
        user.experienceMonths = request.experienceMonths

        // TODO - 관심 아르바이트, 아르바이트 경험 저장

        return saveUser(user)
    }
}