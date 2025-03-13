package com.arabyte.arabyteapi.auth.controller

import com.arabyte.arabyteapi.auth.dto.KakaoUserResponse
import com.arabyte.arabyteapi.auth.service.KakaoAuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth/kakao")
class KakaoAuthController(
    private val kakaoAuthService: KakaoAuthService
) {
    @GetMapping("/callback")
    fun kakaoLogin(@RequestParam("code") code: String): ResponseEntity<String> {
        val accessToken = kakaoAuthService.getAccessToken(code)
        return ResponseEntity.ok("Access Token: $accessToken")
    }

    @GetMapping("/user")
    fun getUserInfo(@RequestParam("accessToken") accessToken: String): ResponseEntity<KakaoUserResponse> {
        val userInfo = kakaoAuthService.getUserInfo(accessToken)
        return ResponseEntity.ok(userInfo)
    }
}