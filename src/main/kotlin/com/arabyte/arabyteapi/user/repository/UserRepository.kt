package com.arabyte.arabyteapi.user.repository

import com.arabyte.arabyteapi.user.entitiy.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByKakaoId(kakaoId: Long): User?
}