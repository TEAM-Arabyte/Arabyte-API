package com.arabyte.arabyteapi.domain.review.service

import com.arabyte.arabyteapi.domain.company.Company
import com.arabyte.arabyteapi.domain.location.service.LocationService
import com.arabyte.arabyteapi.domain.review.dto.CreateReviewRequest
import com.arabyte.arabyteapi.domain.review.dto.GetReviewsResponse
import com.arabyte.arabyteapi.domain.review.dto.UpdateReviewRequest
import com.arabyte.arabyteapi.domain.review.entity.Review
import com.arabyte.arabyteapi.domain.review.repository.ReviewRepository
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val locationService: LocationService
) {
    fun getLatestReviews(page: Int, size: Int): Page<GetReviewsResponse> {
        return reviewRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
            .map { GetReviewsResponse.of(it) }
    }

    fun getReview(reviewId: Long): Review {
        return reviewRepository.findById(reviewId).orElseThrow {
            CustomException(CustomError.REVIEW_NOT_FOUND)
        }
    }

    fun createReview(user: User, body: CreateReviewRequest): Review {
        // todo company 구현 후 수정
        val company = Company()
        val location = locationService.findById(body.locationId)

        val review = Review(
            user = user,
            location = location,
            category = body.category,
            rating = body.rating,
            text = body.text,
            workIntensity = body.workIntensity,
            workAtmosphere = body.workAtmosphere,
            salary = body.salary,
            salaryDate = body.salaryDate,
            overtime = body.overtime,
            difficulty = body.difficulty,
            company = company,
        )
        return reviewRepository.save(review)
    }

    fun updateReview(user: User, reviewId: Long, body: UpdateReviewRequest): Review {
        val review = reviewRepository.findById(reviewId).orElseThrow {
            CustomException(CustomError.REVIEW_NOT_FOUND)
        }
        if (review.user != user) {
            throw CustomException(CustomError.REVIEW_NOT_AUTHORIZED)
        }

        body.apply {
            review.text = text
            review.rating = rating
            review.workIntensity = workIntensity
            review.workAtmosphere = workAtmosphere
            review.salary = salary
            review.salaryDate = salaryDate
            review.overtime = overtime
            review.difficulty = difficulty
        }

        return reviewRepository.save(review)
    }

    @Transactional
    fun deleteReview(user: User, reviewId: Long): Review {
        val review = reviewRepository.findById(reviewId).orElseThrow {
            CustomException(CustomError.REVIEW_NOT_FOUND)
        }
        if (review.user != user) {
            throw CustomException(CustomError.REVIEW_NOT_AUTHORIZED)
        }

        reviewRepository.delete(review)

        return review
    }
}