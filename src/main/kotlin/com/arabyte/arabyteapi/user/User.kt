package com.arabyte.arabyteapi.user

import com.arabyte.arabyteapi.common.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class User(
    var kakaoId: Long,
    var username: String,
    var nickname: String,
    var location: String,
    val ageRange: String,
    val gender: String,

    var email: String,
    var phoneNumber: String,

    // 아르바이트 경력 (년/개월)
    var experienceYears: Int = 0,
    var experienceMonths: Int = 0,

    // 아르바이트 관심분야
    // @ElementCollection
    // @Enumerated(EnumType.STRING)
    // var jobInterests: MutableList<JobCategory> = mutableListOf()
) : BaseEntity()