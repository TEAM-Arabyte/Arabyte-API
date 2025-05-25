package com.arabyte.arabyteapi.domain.review.repository

import com.arabyte.arabyteapi.domain.location.entity.QLocation
import com.arabyte.arabyteapi.domain.review.entity.QReview
import com.arabyte.arabyteapi.domain.review.entity.Review
import com.arabyte.arabyteapi.domain.review.enums.Category
import com.querydsl.core.types.dsl.BooleanExpression
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
        val location = QLocation.location

// 주어진 locationId로 조회
        val targetLocation = queryFactory.selectFrom(location)
            .where(location.id.eq(locationId))
            .fetchOne() ?: throw IllegalArgumentException("Location not found")

        val conditions = mutableListOf<BooleanExpression>()

        when (targetLocation.depth) {
            1 -> {
                // sido_code만 같은 모든 지역
                conditions += location.sidoCode.eq(targetLocation.sidoCode)
            }

            2 -> {
                // sido_code와 gu_code가 같고, depth가 2 또는 3
                conditions += location.sidoCode.eq(targetLocation.sidoCode)
                conditions += location.guCode.eq(targetLocation.guCode)
                conditions += location.depth.goe(2)  // depth in (2, 3)
            }

            3 -> {
                // sido_code, gu_code, dong_code 모두 같아야 함
                conditions += location.sidoCode.eq(targetLocation.sidoCode)
                conditions += location.guCode.eq(targetLocation.guCode)
                conditions += location.dongCode.eq(targetLocation.dongCode)
                conditions += location.depth.eq(3)
            }
        }

        val results = queryFactory.selectFrom(location)
            .where(conditions.reduce { acc, condition -> acc.and(condition) })
            .fetch()


        val query = queryFactory.selectFrom(review)
            .where(
                locationId?.let { review.location.id.`in`(results.map { it.id }) },
                categories?.let { review.category.`in`(it) },
                certified?.let { review.isCertified.eq(it) }
            )

        return query.fetch()
    }
}