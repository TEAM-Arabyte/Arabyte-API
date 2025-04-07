package com.arabyte.arabyteapi.domain.auth.controller

import com.arabyte.arabyteapi.domain.auth.dto.KakaoUserResponse
import com.arabyte.arabyteapi.domain.auth.dto.RefreshAccessTokenRequestBody
import com.arabyte.arabyteapi.domain.auth.dto.RefreshAccessTokenResponse
import com.arabyte.arabyteapi.domain.auth.dto.TokenWithUserResponse
import com.arabyte.arabyteapi.domain.auth.service.KakaoAuthService
import com.arabyte.arabyteapi.domain.auth.util.JwtProvider
import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.enums.CustomExceptionGroup
import com.arabyte.arabyteapi.global.exception.CustomException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth/kakao")
class KakaoAuthController(
    private val kakaoAuthService: KakaoAuthService,
    private val jwtProvider: JwtProvider,
) {
    @Operation(summary = "카카오 콜백 처리", description = "카카오 oauth callback을 처리하는 API 입니다.")
    @GetMapping("/callback")
    @SwaggerCustomException(CustomExceptionGroup.KAKAO_LOGIN)
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

    @Operation(summary = "카카오 로그인 or 회원가입", description = "카카오 로그인 또는 회원가입을 처리합니다.")
    @PostMapping("/login")
    @SwaggerCustomException(CustomExceptionGroup.LOGIN_OR_REGISTER)
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

    @Operation(summary = "액세스 토큰 갱신", description = "refresh token을 이용하여 access token을 갱신합니다.")
    @PostMapping("/refresh")
    @SwaggerCustomException(CustomExceptionGroup.REFRESH_ACCESS_TOKEN)
    fun refreshAccessToken(
        @RequestBody body: RefreshAccessTokenRequestBody
    ): RefreshAccessTokenResponse {
        if (!jwtProvider.isValidToken(body.refreshToken)) {
            throw CustomException(CustomError.INVALID_TOKEN)
        }

        val userId = jwtProvider.getUserId(body.refreshToken)
        val accessToken = jwtProvider.generateAccessToken(userId)
        return RefreshAccessTokenResponse(accessToken)
    }
}