package com.arabyte.arabyteapi.domain.review.repository

import com.arabyte.arabyteapi.domain.review.entity.Review
import com.arabyte.arabyteapi.domain.review.entity.ReviewHelpful
import com.arabyte.arabyteapi.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewHelpfulRepository : JpaRepository<ReviewHelpful, Long> {
    fun findByReviewAndUser(review: Review, user: User): ReviewHelpful?
}