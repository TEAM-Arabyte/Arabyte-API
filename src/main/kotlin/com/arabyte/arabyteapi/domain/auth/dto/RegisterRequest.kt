package com.arabyte.arabyteapi.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequest(
    @JsonProperty("userId")
    val userId: Long,

    @JsonProperty("nickname")
    val nickname: String,

    @JsonProperty("ageRange")
    val ageRange: String,

    @JsonProperty("gender")
    val gender: String,

    @JsonProperty("locationId")
    val locationId: Long

)