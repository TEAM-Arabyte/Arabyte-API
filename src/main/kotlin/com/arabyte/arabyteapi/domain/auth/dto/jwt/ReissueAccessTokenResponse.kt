package com.arabyte.arabyteapi.domain.auth.dto.jwt

data class ReissueAccessTokenResponse(
    val accessToken: String
) {
    companion object {
        fun of(accessToken: String): ReissueAccessTokenResponse {
            return ReissueAccessTokenResponse(
                accessToken = accessToken
            )
        }
    }
}
