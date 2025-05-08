package com.arabyte.arabyteapi.domain.report.service

import com.arabyte.arabyteapi.domain.discord.service.DiscordService
import com.arabyte.arabyteapi.domain.report.dto.CreateReportRequest
import com.arabyte.arabyteapi.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class ReportService(
    private val discordService: DiscordService
) {
    fun createReport(user: User, body: CreateReportRequest) {
        val message = """
            ${user.nickname} (${user.id}) 님이 ${body.reportType.description} (${body.targetId})을(를) 신고하였습니다.
            신고 사유: ${body.reason}
            신고자: ${user.nickname} (${user.id})
        """.trimIndent()

        discordService.sendReport(
            message
        )
    }
}