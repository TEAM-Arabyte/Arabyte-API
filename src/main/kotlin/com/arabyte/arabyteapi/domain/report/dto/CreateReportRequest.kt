package com.arabyte.arabyteapi.domain.report.dto

import com.arabyte.arabyteapi.domain.report.enums.ReportType

class CreateReportRequest(
    val reportType: ReportType,
    val targetId: Long,
    val reason: String,
)