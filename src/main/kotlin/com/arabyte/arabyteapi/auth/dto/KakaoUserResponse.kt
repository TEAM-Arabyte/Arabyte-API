package com.arabyte.arabyteapi.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserResponse(
    @JsonProperty("id") val id: Long,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount
)

data class KakaoAccount(
    @JsonProperty("profile") val profile: KakaoProfile,
    @JsonProperty("email") val email: String?,
    @JsonProperty("gender") val gender: String,
    @JsonProperty("age_range") val ageRange: String,
    @JsonProperty("birthday") val birthday: String,
    @JsonProperty("birthyear") val birthyear: String
)

data class KakaoProfile(
    @JsonProperty("nickname") val nickname: String,
    @JsonProperty("profile_image_url") val profileImageUrl: String?
)