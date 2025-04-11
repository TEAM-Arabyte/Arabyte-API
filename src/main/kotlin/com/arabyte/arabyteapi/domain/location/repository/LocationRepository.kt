package com.arabyte.arabyteapi.domain.location.repository

import com.arabyte.arabyteapi.domain.location.entity.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<Location, Long> {
    fun findAllByDepth(depth: Int): List<Location>
    fun findAllBySidoCodeAndDepth(sidoCode: String, depth: Int): List<Location>
    fun findAllBySidoCodeAndGuCodeAndDepth(
        sidoCode: String,
        guCode: String,
        depth: Int
    ): List<Location>
}