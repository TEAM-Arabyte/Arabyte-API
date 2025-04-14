package com.arabyte.arabyteapi.domain.user.controller

import com.arabyte.arabyteapi.domain.user.dto.DeleteUserResponse
import com.arabyte.arabyteapi.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @DeleteMapping
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 하고 삭제된 userId를 반환합니다.")
    fun withdrawUser(
        @RequestParam userId: Long
    ): DeleteUserResponse {
        userService.deleteUserById(userId)
        return DeleteUserResponse(userId = userId)
    }
}