package com.arabyte.arabyteapi.domain.discord.api

import okhttp3.FormBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface DiscordReportApi {
    @POST
    fun executeWebhook(
        @Url webhookUrl: String,
        @Body request: FormBody
    ): Call<Void>
}