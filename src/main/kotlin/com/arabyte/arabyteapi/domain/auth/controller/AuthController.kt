package com.arabyte.arabyteapi.domain.auth.controller

import com.arabyte.arabyteapi.domain.auth.dto.AuthorizeResponse
import com.arabyte.arabyteapi.domain.auth.dto.RegisterRequest
import com.arabyte.arabyteapi.domain.auth.dto.RegisterResponse
import com.arabyte.arabyteapi.domain.auth.dto.jwt.ReissueAccessTokenRequest
import com.arabyte.arabyteapi.domain.auth.dto.jwt.ReissueAccessTokenResponse
import com.arabyte.arabyteapi.domain.auth.service.AuthService
import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import com.arabyte.arabyteapi.global.enums.CustomExceptionGroup
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {
    @Operation(
        summary = "카카오 로그인", description = "카카오 AccessToken을 받아와 기존 회원의 경우 로그인 완료, 기존 회원이 아닌 경우 " +
                "카카오 계정 정보를 DB에 저장만 합니다."
    )
    @PostMapping("/kakao/authorize")
    @SwaggerCustomException(CustomExceptionGroup.KAKAO_LOGIN)
    fun loginWithKakaoAccessToken(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorizationHeader: String
    ): AuthorizeResponse {
        val kakaoAccessToken = authorizationHeader.removePrefix("Bearer ").trim()
        return authService.authorizeWithKakao(kakaoAccessToken)
    }

    // TODO - jwt 처리 필요
    @Operation(summary = "회원가입", description = "회원 가입이 완료되는 시점에 카카오 정보 기반으로 사용자를 DB에 저장합니다.")
    @PatchMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): RegisterResponse {
        return authService.registerUser(request)
    }

    @Operation(summary = "액세스 토큰 갱신", description = "refresh token을 이용하여 access token을 갱신합니다.")
    @PostMapping("/refresh")
    @SwaggerCustomException(CustomExceptionGroup.REFRESH_ACCESS_TOKEN)
    fun refreshAccessToken(
        @RequestBody request: ReissueAccessTokenRequest
    ): ReissueAccessTokenResponse {
        return authService.reissueAccessToken(request)
    }
}