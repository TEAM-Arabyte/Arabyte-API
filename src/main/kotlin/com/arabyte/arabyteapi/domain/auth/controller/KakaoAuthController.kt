package com.arabyte.arabyteapi.domain.auth.controller

import com.arabyte.arabyteapi.domain.auth.dto.*
import com.arabyte.arabyteapi.domain.auth.service.KakaoAuthService
import com.arabyte.arabyteapi.domain.auth.util.JwtProvider
import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.enums.CustomExceptionGroup
import com.arabyte.arabyteapi.global.exception.CustomException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth/kakao")
class KakaoAuthController(
    private val kakaoAuthService: KakaoAuthService,
    private val jwtProvider: JwtProvider,
) {
    @Operation(
        summary = "카카오 로그인과 기존 유저 판별",
        description = "카카오 AccessToken을 헤더로 받아와 로그인하거나, 회원이 아니면 409와 카카오유저정보를 반환합니다." +
                "반환받은 카카오 유저 정보를 통해서 회원가입에 사용하시면 됩니다."
    )
    @PostMapping("/login")
    @SwaggerCustomException(CustomExceptionGroup.LOGIN_OR_REGISTER)
    fun loginWithKakaoAccessToken(
        @RequestHeader("Authorization") authorizationHeader: String
    ): ResponseEntity<Any> {
        val kakaoAccessToken = authorizationHeader.removePrefix("Bearer ").trim()
        val kakaoUser = kakaoAuthService.getKakaoUserInfo(kakaoAccessToken)
        val user = kakaoAuthService.findUserByKakaoId(kakaoUser.id)

        return if (user != null) {
            val accessToken = jwtProvider.generateAccessToken(user.id.toString())
            val refreshToken = jwtProvider.generateRefreshToken(user.id.toString())

            ResponseEntity.ok(TokenWithUserResponse(accessToken, refreshToken, user))
        } else {
            ResponseEntity.status(409).body(kakaoUser)
        }
    }

    @Operation(summary = "회원가입", description = "회원 가입이 완료되는 시점에 카카오 정보 기반으로 사용자를 DB에 저장합니다.")
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterUserRequest
    ): TokenWithUserResponse {
        val user = kakaoAuthService.registerUser(request)
        val accessToken = jwtProvider.generateAccessToken(user.id.toString())
        val refreshToken = jwtProvider.generateRefreshToken(user.id.toString())

        return TokenWithUserResponse(accessToken, refreshToken, user)
    }

    @Operation(summary = "닉네임 중복확인", description = "닉네임 중복을 여부를 반환합니다.")
    @GetMapping("/check-duplicate")
    fun checkNickNameDuplicate(
        @RequestParam nickname: String
    ): NickNameDuplicateResponse {
        val isDuplicate = kakaoAuthService.isNickNameDuplicate(nickname);
        return NickNameDuplicateResponse(isDuplicate)
    }

    @Operation(
        summary = "온보딩 정보 등록", description = "온보딩 화면에서 추가적으로 아르바이트 경력과 관심 직무를 회원정보에 저장합니다.\n" +
                "관심직무는 아직 카테고리가 완전히 구현되지 않아서 추후에 추가될 예정입니다."
    )
    @PostMapping("/onboarding")
    fun onboarding(
        @RequestBody request: OnboardingRequest
    ): OnboardingResponse {
        val user = kakaoAuthService.updateOnboarding(request)
        return OnboardingResponse(
            message = "온보딩 정보가 저장되었습니다.",
            userId = user.id
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