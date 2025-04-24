package com.arabyte.arabyteapi.domain.mypage.dto

data class UpdateBasicInfoResponse(
    val locationId: Long,
    val ageRange: String,
    val gender: String
)