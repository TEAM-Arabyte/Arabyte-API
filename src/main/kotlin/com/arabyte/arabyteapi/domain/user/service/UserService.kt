package com.arabyte.arabyteapi.domain.user.service

import com.arabyte.arabyteapi.domain.user.dto.CheckNickNameResponse
import com.arabyte.arabyteapi.domain.user.dto.DeleteUserResponse
import com.arabyte.arabyteapi.domain.user.dto.OnboardingRequest
import com.arabyte.arabyteapi.domain.user.dto.OnboardingResponse
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.entity.UserJobInterest
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
    fun deleteUserById(userId: Long): DeleteUserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { CustomException(CustomError.USER_NOT_FOUND) }

        userRepository.delete(user)

        return DeleteUserResponse(
            userId = user.id
        )
    }

    private fun isNicknameExists(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    fun checkNickNameDuplicate(nickname: String): CheckNickNameResponse {
        val isDuplicate = isNicknameExists(nickname)

        return CheckNickNameResponse(
            isDuplicate = isDuplicate,
        )
    }

    fun updateOnboarding(request: OnboardingRequest): OnboardingResponse {
        val user = getUserByUserId(request.userId)

        user.experienceYears = request.experienceYears
        user.experienceMonths = request.experienceMonths

        if (request.jobInterests.isNotEmpty()) {
            val interests = request.jobInterests
            val interestEntity = UserJobInterest(
                user = user,
                category1 = interests.getOrNull(0),
                category2 = interests.getOrNull(1),
                category3 = interests.getOrNull(2)
            )
            user.jobInterests = interestEntity
        }

        return OnboardingResponse(
            userId = saveUser(user).id
        )
    }
}