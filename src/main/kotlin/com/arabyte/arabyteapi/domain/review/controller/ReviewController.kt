package com.arabyte.arabyteapi.domain.review.controller

import com.arabyte.arabyteapi.domain.review.dto.CreateReviewRequest
import com.arabyte.arabyteapi.domain.review.dto.GetReviewsResponse
import com.arabyte.arabyteapi.domain.review.dto.UpdateReviewRequest
import com.arabyte.arabyteapi.domain.review.entity.Review
import com.arabyte.arabyteapi.domain.review.service.ReviewService
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.annotation.RequestUser
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reviews")
class ReviewController(
    private val reviewService: ReviewService,
) {
    @Operation(summary = "최신 리뷰 조회", description = "최신 리뷰 목록을 조회합니다.")
    @GetMapping
    fun getReviews(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): Page<GetReviewsResponse> {
        return reviewService.getLatestReviews(page, size)
    }

    @Operation(summary = "리뷰 상세 조회", description = "리뷰의 상세 정보를 조회합니다.")
    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable reviewId: Long
    ): Review {
        return reviewService.getReview(reviewId)
    }

    @Operation(summary = "리뷰 등록", description = "리뷰를 등록합니다.")
    @PostMapping
    fun createReview(
        @RequestUser user: User,
        @RequestBody body: CreateReviewRequest
    ): Review {
        return reviewService.createReview(user, body)
    }

    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.")
    @PutMapping("/{reviewId}")
    fun updateReview(
        @RequestUser user: User,
        @PathVariable reviewId: Long,
        @RequestBody body: UpdateReviewRequest
    ): Review {
        return reviewService.updateReview(user = user, reviewId = reviewId, body = body)
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @RequestUser user: User,
        @PathVariable reviewId: Long
    ): Review {
        return reviewService.deleteReview(user = user, reviewId = reviewId)
    }
}