package com.arabyte.arabyteapi.domain.auth.service

import com.arabyte.arabyteapi.domain.auth.api.KakaoAuthApi
import com.arabyte.arabyteapi.domain.auth.api.KakaoUserApi
import com.arabyte.arabyteapi.domain.auth.dto.KakaoUserResponse
import com.arabyte.arabyteapi.domain.auth.dto.OnboardingRequest
import com.arabyte.arabyteapi.domain.auth.dto.RegisterUserRequest
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.service.UserService
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KakaoAuthService(
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private val redirectUri: String,
    private val userService: UserService,
    private val kakaoAuthApi: KakaoAuthApi,
    private val kakaoUserApi: KakaoUserApi
) {
    fun getKakaoUserInfo(accessToken: String): KakaoUserResponse {
        val response = kakaoUserApi.getUserInfo("Bearer $accessToken").execute()
        return response.body() ?: throw CustomException(CustomError.GET_KAKAO_USER_INFO_FAILED)
    }

    fun findUserByKakaoId(kakaoId: Long): User? = userService.getUserByKakaoId(kakaoId)

    @Transactional
    fun registerUser(request: RegisterUserRequest): User {
        if (userService.getUserByKakaoId(request.kakaoId) != null) {
            throw CustomException(CustomError.USER_NOT_FOUND)
        }

        val user = User(
            kakaoId = request.kakaoId,
            username = request.username,
            nickname = request.nickname,
            profileImageUrl = request.profileImageUrl,
            ageRange = request.ageRange,
            gender = request.gender,
            email = request.email,
            phoneNumber = request.phoneNumber,
            location = request.location,
        )

        return userService.saveUser(user)
    }

    fun isNickNameDuplicate(nickname: String): Boolean {
        return userService.isNicknameExists(nickname)
    }


    fun updateOnboarding(request: OnboardingRequest): User {
        val user = userService.getUser(request.userId)
        user.experienceYears = request.experienceYears
        user.experienceMonths = request.experienceMonths

        // TODO - 관심 아르바이트, 아르바이트 경험 저장

        return userService.saveUser(user)
    }
}