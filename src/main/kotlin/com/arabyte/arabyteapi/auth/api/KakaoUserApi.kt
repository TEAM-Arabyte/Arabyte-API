package com.arabyte.arabyteapi.auth.api

import com.arabyte.arabyteapi.auth.dto.KakaoUserResponse
import org.springframework.http.HttpHeaders
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface KakaoUserApi {
    @GET("/v2/user/me")
    fun getUserInfo(
        @Header(HttpHeaders.AUTHORIZATION) accessToken: String
    ): Call<KakaoUserResponse>
}