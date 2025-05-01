package com.arabyte.arabyteapi.domain.review.dto

import com.arabyte.arabyteapi.domain.review.entity.Review

class GetReviewsResponse(
    val reviewId: Int,
    val isCertified: Boolean,
    val star: Int,
    val text: String,
    val location: String,
    val category: String
) {
    companion object {
        fun of(review: Review): GetReviewsResponse {
            return GetReviewsResponse(
                reviewId = review.id.toInt(),
                isCertified = review.isCertified,
                star = review.rating,
                text = review.text,
                location = review.location.toString(),
                category = review.category.name
            )
        }
    }
}