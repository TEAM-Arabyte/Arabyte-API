package com.arabyte.arabyteapi.domain.user.entity

import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "`user`")
class User(
    var kakaoId: Long,
    var username: String,
    var nickname: String,
    var profileImageUrl: String,

    val ageRange: String,
    val gender: String,
    var email: String,
    var phoneNumber: String?,

    // 거주지역
    var location: String,

    // 아르바이트 경력 (년/개월)
    var experienceYears: Int = 0,
    var experienceMonths: Int = 0,

    // 아르바이트 관심분야
    // @ElementCollection
    // @Enumerated(EnumType.STRING)
    // var jobInterests: MutableList<JobCategory> = mutableListOf()
) : BaseEntity()