package com.arabyte.arabyteapi.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true) // 최상위에도 꼭 추가
data class KakaoUserResponse(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class KakaoAccount(
        @JsonProperty("profile")
        val profile: KakaoProfile,

        @JsonProperty("name")
        val name: String,

        @JsonProperty("birthday")
        val birthday: String,

        @JsonProperty("birthyear")
        val birthyear: String,

        @JsonProperty("age_range")
        val ageRange: String,

        @JsonProperty("gender")
        val gender: String,

        @JsonProperty("email")
        val email: String?,

        @JsonProperty("phone_number")
        val phoneNumber: String?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class KakaoProfile(
        @JsonProperty("nickname")
        val nickname: String,

        @JsonProperty("profile_image_url")
        val profileImageUrl: String,

        @JsonProperty("thumbnail_image_url")
        val thumbnailImageUrl: String,

        @JsonProperty("is_default_image")
        val isDefaultImage: Boolean
    )
}
