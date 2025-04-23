package com.arabyte.arabyteapi.domain.user.controller

import com.arabyte.arabyteapi.domain.user.dto.*
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.domain.user.service.UserService
import com.arabyte.arabyteapi.global.annotation.RequestUser
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @DeleteMapping
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 하고 삭제된 계정의 userId를 반환합니다.")
    fun withdrawUser(
        @RequestUser user: User
    ): DeleteUserResponse {
        return userService.deleteUserById(user.id)
    }

    @Operation(summary = "온보딩 정보 등록", description = "온보딩 화면에서 추가적으로 아르바이트 경력과 관심 직무를 회원정보에 저장합니다.")
    @PostMapping("/onboarding")
    fun onboarding(
        @RequestUser user: User,
        @RequestBody request: OnboardingRequest
    ): OnboardingResponse {
        return userService.updateOnboarding(user, request)
    }

    @Operation(summary = "닉네임 중복확인", description = "닉네임이 이미 존재하는지 확인합니다.")
    @GetMapping("/nickname/check")
    fun checkNickNameDuplicate(
        @RequestUser user: User,
        @RequestParam nickname: String
    ): CheckNickNameResponse {
        return userService.checkNickNameDuplicate(nickname, user.id)
    }

    @Operation(summary = "닉네임 수정", description = "마이페이지에서 닉네임을 수정합니다.")
    @PatchMapping("/nickname/update")
    fun updateNickName(
        @RequestUser user: User,
        @RequestParam newNickname: String
    ): UpdateNickNameResponse {
        return userService.updateNickName(user, newNickname)
    }
}