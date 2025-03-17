package com.arabyte.arabyteapi.auth.api

import com.arabyte.arabyteapi.auth.dto.KakaoTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface KakaoAuthApi {
    @FormUrlEncoded
    @POST("/oauth/token")
    fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String
    ): Call<KakaoTokenResponse>
}