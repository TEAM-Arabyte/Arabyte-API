package com.arabyte.arabyteapi.global.configuration

import com.arabyte.arabyteapi.domain.auth.api.KakaoAuthApi
import com.arabyte.arabyteapi.domain.auth.api.KakaoUserApi
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class RetrofitConfiguration(
    private val objectMapper: ObjectMapper
) {
    @Bean("okHttpClient")
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder().apply {
                connectTimeout(15, TimeUnit.SECONDS)
                writeTimeout(15, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
                callTimeout(15, TimeUnit.SECONDS)
            }.build()
    }

    @Bean("kakaoAuthApi")
    fun kakaoAuthApi(okHttpClient: OkHttpClient): KakaoAuthApi {
        return Retrofit.Builder()
            .baseUrl("https://kauth.kakao.com")
            .client(okHttpClient)
            .addConverterFactory(
                JacksonConverterFactory.create(objectMapper)
            )
            .build()
            .create(KakaoAuthApi::class.java)
    }

    @Bean("kakaoUserApi")
    fun kakaoUserApi(okHttpClient: OkHttpClient): KakaoUserApi {
        return Retrofit.Builder()
            .baseUrl("https://kapi.kakao.com")
            .client(okHttpClient)
            .addConverterFactory(
                JacksonConverterFactory.create(objectMapper)
            )
            .build()
            .create(KakaoUserApi::class.java)
    }
}