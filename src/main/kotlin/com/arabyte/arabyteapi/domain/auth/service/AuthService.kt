package com.arabyte.arabyteapi.domain.auth.service

import com.arabyte.arabyteapi.domain.auth.api.KakaoUserApi
import com.arabyte.arabyteapi.domain.auth.dto.AuthorizeResponse
import com.arabyte.arabyteapi.domain.auth.dto.RegisterRequest
import com.arabyte.arabyteapi.domain.auth.dto.RegisterResponse
import com.arabyte.arabyteapi.domain.auth.dto.jwt.ReissueAccessTokenRequest
import com.arabyte.arabyteapi.domain.auth.dto.jwt.ReissueAccessTokenResponse
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
    private fun getKakaoUserInfo(accessToken: String): KakaoUserResponse {
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
            val savedUser = userService.saveUser(user)
            val accessToken = jwtProvider.generateAccessToken(user.id.toString())
            val refreshToken = jwtProvider.generateRefreshToken(user.id.toString())

            return AuthorizeResponse(savedUser.id, accessToken, refreshToken, false)
        }
        val accessToken = jwtProvider.generateAccessToken(user.id.toString())
        val refreshToken = jwtProvider.generateRefreshToken(user.id.toString())

        return AuthorizeResponse(user.id, accessToken, refreshToken, true)
    }

    @Transactional
    fun registerUser(user: User, request: RegisterRequest): RegisterResponse {
        val location = locationService.findById(request.locationId)

        user.nickname = request.nickname
        user.ageRange = request.ageRange
        user.gender = request.gender
        user.location = location

        val savedUser = userService.saveUser(user)

        return RegisterResponse(true, savedUser.id)
    }

    private fun getUserIdByToken(body: ReissueAccessTokenRequest): String {
        if (!jwtProvider.isValidToken(body.refreshToken)) {
            throw CustomException(CustomError.INVALID_TOKEN)
        }
        return jwtProvider.getUserId(body.refreshToken)
    }

    fun reissueAccessToken(request: ReissueAccessTokenRequest): ReissueAccessTokenResponse {
        val userId = getUserIdByToken(request)
        val accessToken = jwtProvider.generateAccessToken(userId.toString())

        return ReissueAccessTokenResponse(accessToken)
    }
}