package com.arabyte.arabyteapi.domain.user.controller

import com.arabyte.arabyteapi.domain.user.dto.CheckNickNameResponse
import com.arabyte.arabyteapi.domain.user.dto.DeleteUserResponse
import com.arabyte.arabyteapi.domain.user.dto.OnboardingRequest
import com.arabyte.arabyteapi.domain.user.dto.OnboardingResponse
import com.arabyte.arabyteapi.domain.user.service.UserService
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
        @RequestParam userId: Long
    ): DeleteUserResponse {
        userService.deleteUserById(userId)
        return DeleteUserResponse(userId = userId)
    }

    @Operation(
        summary = "온보딩 정보 등록", description = "온보딩 화면에서 추가적으로 아르바이트 경력과 관심 직무를 회원정보에 저장합니다.\n" +
                "관심직무는 아직 카테고리가 완전히 구현되지 않아서 추후에 추가될 예정입니다."
    )
    @PostMapping("/onboarding")
    fun onboarding(
        @RequestBody request: OnboardingRequest
    ): OnboardingResponse {
        val userId = userService.updateOnboarding(request)
        return OnboardingResponse(userId)
    }

    @Operation(summary = "닉네임 중복확인", description = "닉네임이 이미 존재하는지 확인합니다.")
    @GetMapping("/nickname/check")
    fun checkNickNameDuplicate(
        @RequestParam nickname: String
    ): CheckNickNameResponse {
        return userService.checkNickNameDuplicate(nickname);
    }
}