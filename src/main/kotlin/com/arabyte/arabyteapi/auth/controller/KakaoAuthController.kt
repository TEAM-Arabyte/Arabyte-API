package com.arabyte.arabyteapi.auth.controller

import com.arabyte.arabyteapi.auth.dto.KakaoUserResponse
import com.arabyte.arabyteapi.auth.dto.RefreshAccessTokenRequestBody
import com.arabyte.arabyteapi.auth.dto.RefreshAccessTokenResponse
import com.arabyte.arabyteapi.auth.dto.TokenWithUserResponse
import com.arabyte.arabyteapi.auth.service.KakaoAuthService
import com.arabyte.arabyteapi.auth.util.JwtProvider
import com.arabyte.arabyteapi.common.exception.InvalidTokenException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth/kakao")
class KakaoAuthController(
    private val kakaoAuthService: KakaoAuthService,
    private val jwtProvider: JwtProvider,
) {
    @GetMapping("/callback")
    fun kakaoLogin(
        @RequestParam("code") code: String
    ): TokenWithUserResponse {
        val kakaoAccessToken = kakaoAuthService.getKakaoAccessToken(code)
        val kakaoUser = kakaoAuthService.getKakaoUserInfo(kakaoAccessToken)

        val user = kakaoAuthService.loginOrRegister(kakaoUser)
        val accessToken = jwtProvider.generateAccessToken(user.id.toString())
        val refreshToken = jwtProvider.generateRefreshToken(user.id.toString())

        return TokenWithUserResponse(
            accessToken,
            refreshToken,
            user
        )
    }

    @PostMapping("/login")
    fun loginOrRegister(
        @RequestBody kakaoUser: KakaoUserResponse
    ): TokenWithUserResponse {
        val user = kakaoAuthService.loginOrRegister(kakaoUser)
        val accessToken = jwtProvider.generateAccessToken(user.id.toString())
        val refreshToken = jwtProvider.generateRefreshToken(user.id.toString())

        return TokenWithUserResponse(
            accessToken,
            refreshToken,
            user
        )
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody body: RefreshAccessTokenRequestBody
    ): RefreshAccessTokenResponse {
        if (!jwtProvider.isValidToken(body.refreshToken)) {
            throw InvalidTokenException()
        }

        val userId = jwtProvider.getUserId(body.refreshToken)
        val accessToken = jwtProvider.generateAccessToken(userId)
        return RefreshAccessTokenResponse(accessToken)
    }
}