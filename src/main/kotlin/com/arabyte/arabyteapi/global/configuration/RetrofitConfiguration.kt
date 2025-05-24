package com.arabyte.arabyteapi.global.configuration

import com.arabyte.arabyteapi.domain.auth.api.KakaoUserApi
import com.arabyte.arabyteapi.domain.discord.api.DiscordReportApi
import com.arabyte.arabyteapi.domain.mypage.api.OcrApi
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class RetrofitConfiguration(
    private val objectMapper: ObjectMapper,
    @Value("\${ocr.server.base-url}")
    private val baseUrl: String
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

    @Bean("discordReportApi")
    fun discordReportApi(okHttpClient: OkHttpClient): DiscordReportApi {
        return Retrofit.Builder()
            .baseUrl("https://discord.com/api/")
            .client(okHttpClient)
            .addConverterFactory(
                JacksonConverterFactory.create(objectMapper)
            )
            .build()
            .create(DiscordReportApi::class.java)
    }

    @Bean
    fun ocrApi(okHttpClient: OkHttpClient): OcrApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                JacksonConverterFactory.create(objectMapper)
            )
            .build()
            .create(OcrApi::class.java)
    }
}