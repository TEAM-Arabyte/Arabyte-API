package com.arabyte.arabyteapi.domain.company.service

import com.arabyte.arabyteapi.domain.company.dto.CreateCompanyRequest
import com.arabyte.arabyteapi.domain.company.entity.Company
import com.arabyte.arabyteapi.domain.company.repository.CompanyRepository
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
) {

    fun findById(id: Long) = companyRepository.findById(id)
        .orElseThrow {
            CustomException(CustomError.COMPANY_NOT_FOUND)
        }

    fun findByMapId(mapId: String) = companyRepository.findByMapId(mapId)

    fun createCompany(body: CreateCompanyRequest): Company {
        if (findByMapId(body.mapId) != null) {
            throw CustomException(CustomError.DUPLICATE_COMPANY)
        }

        return companyRepository.save(
            Company(
                mapId = body.mapId,
                name = body.name,
            )
        )
    }
}