package com.arabyte.arabyteapi.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequest(
    @JsonProperty("userId")
    val userId: Long,

    @JsonProperty("nickname")
    val nickname: String,

    @JsonProperty("age_range")
    val ageRange: String,

    @JsonProperty("gender")
    val gender: String,

    @JsonProperty("location_id")
    val locationId: Long
)