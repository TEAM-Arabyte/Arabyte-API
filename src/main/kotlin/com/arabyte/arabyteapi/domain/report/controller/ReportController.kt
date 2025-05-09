package com.arabyte.arabyteapi.domain.report.controller

import com.arabyte.arabyteapi.domain.report.dto.CreateReportRequest
import com.arabyte.arabyteapi.domain.report.service.ReportService
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.annotation.RequestUser
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports")
class ReportController(
    private val reportService: ReportService,
) {
    @Operation(summary = "신고하기", description = "신고를 진행합니다. 게시글, 댓글, 리뷰 신고를 지원합니다.")
    @PostMapping
    fun createReport(
        @RequestUser user: User,
        @RequestBody body: CreateReportRequest
    ) {
        return reportService.createReport(user, body)
    }

}