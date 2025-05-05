package com.arabyte.arabyteapi.domain.company.repository

import com.arabyte.arabyteapi.domain.company.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {
    fun findByMapId(mapId: String): Company?
}