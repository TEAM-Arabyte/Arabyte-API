package com.arabyte.arabyteapi.domain.user.repository

import com.arabyte.arabyteapi.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByKakaoId(kakaoId: Long): User?
    fun existsByNickname(nickname: String): Boolean
}