package com.arabyte.arabyteapi.domain.user.dto

import com.arabyte.arabyteapi.domain.review.enums.Category

data class UpdateJobInterestRequest(
    val userId: Long,
    val jobInterests: List<Category> = listOf()
)
