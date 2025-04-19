package com.arabyte.arabyteapi.domain.review.repository

import com.arabyte.arabyteapi.domain.review.entity.Review
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Review>
}