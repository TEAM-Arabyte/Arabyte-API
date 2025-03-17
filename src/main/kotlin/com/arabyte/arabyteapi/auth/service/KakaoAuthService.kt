package com.arabyte.arabyteapi.auth.service

import com.arabyte.arabyteapi.auth.api.KakaoAuthApi
import com.arabyte.arabyteapi.auth.api.KakaoUserApi
import com.arabyte.arabyteapi.auth.dto.KakaoUserResponse
import com.arabyte.arabyteapi.auth.exception.GetKakaoAccessTokenFailureException
import com.arabyte.arabyteapi.auth.exception.GetKakaoUserInfoFailureException
import com.arabyte.arabyteapi.user.entitiy.User
import com.arabyte.arabyteapi.user.service.UserService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate

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
    fun getKakaoAccessToken(code: String): String {
        val response = kakaoAuthApi.getAccessToken(
            "authorization_code",
            clientId,
            redirectUri,
            code
        ).execute()

        return response.body()?.accessToken ?: throw GetKakaoAccessTokenFailureException()
    }

    fun getKakaoUserInfo(accessToken: String): KakaoUserResponse {
        val response = kakaoUserApi.getUserInfo("Bearer $accessToken").execute()

        return response.body() ?: throw GetKakaoUserInfoFailureException()
    }

    @Transactional
    fun loginOrRegister(kakaoUser: KakaoUserResponse): User {
        val user = userService.getUserByKakaoId(kakaoUser.id)

        if (user != null) {
            return user
        }

        val currentYear = LocalDate.now().year
        val birthyear = kakaoUser.kakaoAccount.birthyear.toIntOrNull()
        val calculatedAgeRange = when {
            birthyear == null -> "미제공"
            currentYear - birthyear in 0..19 -> "19세 미만"
            currentYear - birthyear in 20..24 -> "20대 초반"
            currentYear - birthyear in 25..29 -> "20대 후반"
            currentYear - birthyear in 30..34 -> "30대 초반"
            currentYear - birthyear in 35..39 -> "30대 후반"
            else -> "40세 이상"
        }

        val newUser = User(
            kakaoId = kakaoUser.id,
            username = kakaoUser.kakaoAccount.name,
            nickname = kakaoUser.kakaoAccount.profile.nickname,
            profileImageUrl = kakaoUser.kakaoAccount.profile.profileImageUrl,
            ageRange = calculatedAgeRange,
            gender = kakaoUser.kakaoAccount.gender,
            email = kakaoUser.kakaoAccount.email ?: "",
            phoneNumber = kakaoUser.kakaoAccount.phoneNumber,
            location = "",
        )

        return userService.saveUser(newUser)
    }
}