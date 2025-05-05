package com.arabyte.arabyteapi.domain.review.repository

import com.arabyte.arabyteapi.domain.review.entity.QReview
import com.arabyte.arabyteapi.domain.review.entity.Review
import com.arabyte.arabyteapi.domain.review.enums.Category
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomReviewRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findAllByCondition(
        locationId: Long?,
        categories: List<Category>?,
        certified: Boolean?
    ): List<Review> {
        val review = QReview.review
        val query = queryFactory.selectFrom(review)
            .where(
                locationId?.let { review.location.id.eq(it) },
                categories?.let { review.category.`in`(it) },
                certified?.let { review.isCertified.eq(it) }
            )

        return query.fetch()
    }
}