package com.arabyte.arabyteapi.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserResponse(
    @JsonProperty("id") val id: Long,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount
) {
    data class KakaoAccount(
        @JsonProperty("profile") val profile: KakaoProfile,
        @JsonProperty("name") val name: String,
        @JsonProperty("birthday") val birthday: String,
        @JsonProperty("birthyear") val birthyear: String,
        @JsonProperty("age_range") val ageRange: String,
        @JsonProperty("gender") val gender: String,
        @JsonProperty("email") val email: String?,
        @JsonProperty("phone_number") val phoneNumber: String?
    ) {
        data class KakaoProfile(
            @JsonProperty("nickname") val nickname: String,
            @JsonProperty("profile_image_url") val profileImageUrl: String,
            @JsonProperty("thumbnail_image_url") val thumbnailImageUrl: String
        )
    }
}
