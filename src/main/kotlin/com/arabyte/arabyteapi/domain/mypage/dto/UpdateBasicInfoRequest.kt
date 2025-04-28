package com.arabyte.arabyteapi.domain.mypage.dto

data class UpdateBasicInfoRequest(
    val locationId: Long,
    val ageRange: String,
    val gender: String
)