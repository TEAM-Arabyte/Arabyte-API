package com.arabyte.arabyteapi.domain.discord.service

import com.arabyte.arabyteapi.domain.discord.api.DiscordReportApi
import okhttp3.FormBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DiscordService(
    @Value("\${discord.report-url}")
    private val reportUrl: String,
    private val discordReportApi: DiscordReportApi
) {
    fun sendReport(message: String) {
        val body = FormBody.Builder()
            .add("content", message)
            .build()

        discordReportApi.executeWebhook(reportUrl, body).execute()
    }
}