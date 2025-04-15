package com.arabyte.arabyteapi.domain.auth.service

import com.arabyte.arabyteapi.domain.auth.api.KakaoUserApi
import com.arabyte.arabyteapi.domain.auth.dto.AuthorizeResponse
import com.arabyte.arabyteapi.domain.auth.dto.LoginResponse
import com.arabyte.arabyteapi.domain.auth.dto.RegisterRequest
import com.arabyte.arabyteapi.domain.auth.dto.RegisterResponse
import com.arabyte.arabyteapi.domain.auth.dto.jwt.RefreshAccessTokenRequestBody
import com.arabyte.arabyteapi.domain.auth.dto.retrofit.KakaoUserResponse
import com.arabyte.arabyteapi.domain.auth.util.JwtProvider
import com.arabyte.arabyteapi.domain.location.service.LocationService
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.service.UserService
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val kakaoUserApi: KakaoUserApi,
    private val jwtProvider: JwtProvider,
    private val locationService: LocationService
) {
    fun getKakaoUserInfo(accessToken: String): KakaoUserResponse {
        val response = kakaoUserApi.getUserInfo("Bearer $accessToken").execute()
        return response.body() ?: throw CustomException(CustomError.GET_KAKAO_USER_INFO_FAILED)
    }

    fun authorizeWithKakao(accessToken: String): AuthorizeResponse {
        val kakaoUser = getKakaoUserInfo(accessToken)
        val user = userService.getUserByKakaoId(kakaoUser.id)

        if (user == null) {
            val user = User(
                kakaoId = kakaoUser.id,
                username = kakaoUser.kakaoAccount.name,
                nickname = kakaoUser.kakaoAccount.kakaoProfile.nickname,
                profileImageUrl = kakaoUser.kakaoAccount.kakaoProfile.profileImageUrl,
                ageRange = kakaoUser.kakaoAccount.ageRange,
                gender = kakaoUser.kakaoAccount.gender,
                email = kakaoUser.kakaoAccount.email,
                phoneNumber = kakaoUser.kakaoAccount.phoneNumber
            )
            userService.saveUser(user)
            return RegisterResponse(false, user.id)
        } else {
            return LoginResponse(
                userId = user.id,
                accessToken = jwtProvider.generateAccessToken(user.id.toString()),
                refreshToken = jwtProvider.generateRefreshToken(user.id.toString())
            )
        }
    }

    @Transactional
    fun registerUser(request: RegisterRequest): User {
        val user = userService.getUserByUserId(request.userId)
        val location = locationService.findById(request.locationId)

        user.nickname = request.nickname
        user.ageRange = request.ageRange
        user.gender = request.gender
        user.location = location


        return userService.saveUser(user)
    }

    fun generateAccessToken(userId: Long): String {
        return jwtProvider.generateAccessToken(userId.toString())
    }

    fun generateRefreshToken(userId: Long): String {
        return jwtProvider.generateRefreshToken(userId.toString())
    }

    fun getUserIdByToken(body: RefreshAccessTokenRequestBody): String {
        if (!jwtProvider.isValidToken(body.refreshToken)) {
            throw CustomException(CustomError.INVALID_TOKEN)
        }
        return jwtProvider.getUserId(body.refreshToken)
    }
}