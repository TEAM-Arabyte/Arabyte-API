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
    @Operation()
    fun withdrawUser(
        @RequestParam userId: Long
    ): DeleteUserResponse {
        userService.deleteUserById(userId)
        return DeleteUserResponse(
            userId = userId,
            message = ""
        )
    }
}