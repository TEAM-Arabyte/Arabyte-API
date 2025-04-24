package com.arabyte.arabyteapi.domain.mypage.dto

import com.arabyte.arabyteapi.domain.user.entity.User

data class MyPageResponse(
    val userId: Long
) {
    companion object {
        fun of(user: User): MyPageResponse {
            return MyPageResponse(
                userId = user.id
            )
        }
    }
}