package com.arabyte.arabyteapi.domain.review.dto

import com.arabyte.arabyteapi.domain.location.entity.Location
import com.arabyte.arabyteapi.domain.review.entity.Review

class GetReviewsResponse(
    val reviewId: Int,
    val isCertified: Boolean,
    val star: Int,
    val text: String,
    val location: Location,
    val category: String
) {
    companion object {
        fun of(review: Review): GetReviewsResponse {
            return GetReviewsResponse(
                reviewId = review.id.toInt(),
                isCertified = review.isCertified,
                star = review.rating,
                text = review.text,
                location = review.location,
                category = review.category.name
            )
        }
    }
}