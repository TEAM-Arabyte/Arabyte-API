package com.arabyte.arabyteapi.domain.user.dto

import com.arabyte.arabyteapi.domain.review.enums.Category

data class OnboardingRequest(
    val userId: Long,
    val experienceYears: Int?,
    val experienceMonths: Int?,
    val jobInterests: List<Category>
)