package com.arabyte.arabyteapi.domain.user.controller

import com.arabyte.arabyteapi.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/withdraw")
    @Operation()
    fun withdrawUser(
        @RequestParam userId: Long
    ): String {
        userService.deleteUserById(userId)
        return "회원 탈퇴가 완료되었습니다."
    }
}