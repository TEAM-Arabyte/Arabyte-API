package com.arabyte.arabyteapi.auth.controller

import com.arabyte.arabyteapi.auth.dto.KakaoUserResponse
import com.arabyte.arabyteapi.auth.service.KakaoAuthService
import com.arabyte.arabyteapi.auth.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth/kakao")
class KakaoAuthController(
    private val kakaoAuthService: KakaoAuthService,
    private val jwtUtil: JwtUtil,
) {
    @GetMapping("/callback")
    fun kakaoLogin(@RequestParam("code") code: String): ResponseEntity<Any> {
        val accessToken = kakaoAuthService.getAccessToken(code)
        val kakaoUser = kakaoAuthService.getUserInfo(accessToken)

        // 로그인 또는 회원가입 진행
        val response = kakaoAuthService.loginOrRegister(kakaoUser)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun loginOrRegister(@RequestBody kakaoUser: KakaoUserResponse): ResponseEntity<Map<String, Any>> {
        val response = kakaoAuthService.loginOrRegister(kakaoUser)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestParam refreshToken: String): ResponseEntity<Any> {
        val userId = jwtUtil.extractUserId(refreshToken)

        return if (userId != null && jwtUtil.validateToken(refreshToken)) {
            val newAccessToken = jwtUtil.generateAccessToken(userId)
            ResponseEntity.ok(mapOf("accessToken" to newAccessToken))
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Invalid Refresh Token"))
        }
    }
}