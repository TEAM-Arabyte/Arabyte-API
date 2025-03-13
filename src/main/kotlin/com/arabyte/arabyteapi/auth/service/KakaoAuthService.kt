package com.arabyte.arabyteapi.auth.service

import com.arabyte.arabyteapi.auth.dto.KakaoTokenResponse
import com.arabyte.arabyteapi.auth.dto.KakaoUserResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KakaoAuthService(
    private val restTemplate: RestTemplate,
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private val redirectUri: String,
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

        println("🔹 요청 데이터: $request") // 요청 데이터 확인 로그

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val requestEntity = HttpEntity(request, headers)
        val response = restTemplate.postForEntity(
            tokenUrl,
            requestEntity,
            KakaoTokenResponse::class.java
        )

        println("🔹 응답 데이터: ${response.body}") // 응답 데이터 확인 로그

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
}