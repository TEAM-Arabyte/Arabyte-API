package com.arabyte.arabyteapi.domain.mypage.dto

import com.arabyte.arabyteapi.domain.review.enums.Category

data class UpdateSubInfoResponse(
    val experienceYears: Int?,
    val experienceMonths: Int?,
    val jobInterests: List<Category>
)
