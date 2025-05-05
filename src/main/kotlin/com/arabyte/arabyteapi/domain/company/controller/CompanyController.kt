package com.arabyte.arabyteapi.domain.company.controller

import com.arabyte.arabyteapi.domain.company.dto.CreateCompanyRequest
import com.arabyte.arabyteapi.domain.company.service.CompanyService
import com.arabyte.arabyteapi.global.annotation.SwaggerCustomException
import com.arabyte.arabyteapi.global.enums.CustomExceptionGroup
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/companies")
class CompanyController(
    private val companyService: CompanyService,
) {
    @Operation(summary = "회사 ID로 회사 조회", description = "회사 ID로 회사를 조회합니다.")
    @GetMapping("/{id}")
    fun getCompanyById(
        @PathVariable("id") id: Long
    ) = companyService.findById(id)

    @Operation(summary = "회사 추가", description = "회사를 추가합니다.")
    @PostMapping
    @SwaggerCustomException(CustomExceptionGroup.CREATE_COMPANY)
    fun createCompany(
        @RequestBody body: CreateCompanyRequest
    ) = companyService.createCompany(body)
}