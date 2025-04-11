package com.arabyte.arabyteapi.domain.auth.dto

data class OnboardingRequest(
    val userId: Long,
    val experienceYears: Int,
    val experienceMonths: Int,

    // TODO - 카테고리 생성 후 구현
    // val jobInterests: List<String>? = null
)