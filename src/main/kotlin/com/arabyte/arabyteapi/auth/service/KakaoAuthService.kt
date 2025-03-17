package com.arabyte.arabyteapi.auth.service

import com.arabyte.arabyteapi.auth.dto.KakaoTokenResponse
import com.arabyte.arabyteapi.auth.dto.KakaoUserResponse
import com.arabyte.arabyteapi.auth.util.JwtUtil
import com.arabyte.arabyteapi.user.entitiy.User
import com.arabyte.arabyteapi.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.time.LocalDate

@Service
class KakaoAuthService(
    private val restTemplate: RestTemplate,
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private val redirectUri: String,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
) {
    private val tokenUrl = "https://kauth.kakao.com/oauth/token"
    private val userInfoUrl = "https://kapi.kakao.com/v2/user/me"

    fun getAccessToken(code: String): String {
        val request = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", clientId)
            add("redirect_uri", redirectUri)
            add("code", code)
        }

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val requestEntity = HttpEntity(request, headers)
        val response = restTemplate.postForEntity(
            tokenUrl,
            requestEntity,
            KakaoTokenResponse::class.java
        )

        return response.body?.accessToken ?: throw RuntimeException("Failed to get access token")
    }

    fun getUserInfo(accessToken: String): KakaoUserResponse {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Authorization", "Bearer $accessToken")
        }

        val requestEntity = HttpEntity(null, headers)
        val response = restTemplate.exchange(
            userInfoUrl,
            HttpMethod.GET,
            requestEntity,
            KakaoUserResponse::class.java
        )

        return response.body ?: throw RuntimeException("Failed to get user info")
    }

    @Transactional
    fun loginOrRegister(kakaoUser: KakaoUserResponse): Map<String, Any> {
        val existingUser = userRepository.findByKakaoId(kakaoUser.id);

        return if (existingUser != null) {
            // 기존 회원인 경우 - JWT 발급 후 반환
            val accessToken = jwtUtil.generateAccessToken(existingUser.id.toString())
            val refreshToken = jwtUtil.generateRefreshToken(existingUser.id.toString())

            mapOf(
                "message" to "로그인 성공",
                "accessToken" to accessToken,
                "refreshToken" to refreshToken
            )
        } else {
            // 기존 회원이 아닌 경우 - 회원가입절차.
            val currentYear = LocalDate.now().year
            val birthyear = kakaoUser.kakaoAccount.birthyear?.toIntOrNull()
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
                location = "",  // 초기값 설정
            )
            // 일단 저장
            val savedUser = userRepository.save(newUser)

            return mapOf(
                "userId" to savedUser.id,
                "nickname" to savedUser.nickname,
                "ageRange" to savedUser.ageRange,
                "gender" to savedUser.gender
            )
        }
    }
}