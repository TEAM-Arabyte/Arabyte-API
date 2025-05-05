package com.arabyte.arabyteapi.domain.review.service

import com.arabyte.arabyteapi.domain.company.service.CompanyService
import com.arabyte.arabyteapi.domain.location.service.LocationService
import com.arabyte.arabyteapi.domain.review.dto.*
import com.arabyte.arabyteapi.domain.review.entity.Review
import com.arabyte.arabyteapi.domain.review.entity.ReviewHelpful
import com.arabyte.arabyteapi.domain.review.enums.Helpful
import com.arabyte.arabyteapi.domain.review.repository.ReviewHelpfulRepository
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
    private val reviewHelpfulRepository: ReviewHelpfulRepository,
    private val locationService: LocationService,
    private val companyService: CompanyService,
) {
    fun getLatestReviews(page: Int, size: Int): Page<GetReviewsResponse> {
        return reviewRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
            .map { GetReviewsResponse.of(it) }
    }

    fun getReview(reviewId: Long): ReviewResponse {
        val review = reviewRepository.findById(reviewId).orElseThrow {
            CustomException(CustomError.REVIEW_NOT_FOUND)
        }

        return ReviewResponse.of(review)
    }

    fun createReview(user: User, body: CreateReviewRequest): ReviewResponse {
        val company = companyService.findById(body.companyId)
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
        return ReviewResponse.of(reviewRepository.save(review))
    }

    fun updateReview(user: User, reviewId: Long, body: UpdateReviewRequest): ReviewResponse {
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

        return ReviewResponse.of(reviewRepository.save(review))
    }

    @Transactional
    fun deleteReview(user: User, reviewId: Long): ReviewResponse {
        val review = reviewRepository.findById(reviewId).orElseThrow {
            CustomException(CustomError.REVIEW_NOT_FOUND)
        }
        if (review.user != user) {
            throw CustomException(CustomError.REVIEW_NOT_AUTHORIZED)
        }

        reviewRepository.delete(review)

        return ReviewResponse.of(review)
    }

    @Transactional
    fun reviewHelpful(user: User, reviewId: Long, body: ReviewHelpfulRequest): ReviewResponse {
        val review = reviewRepository.findById(reviewId).orElseThrow {
            CustomException(CustomError.REVIEW_NOT_FOUND)
        }
        if (review.user == user) {
            throw CustomException(CustomError.CAN_NOT_ADD_HELPFUL_FOR_OWN_REVIEW)
        }

        val reviewHelpful = reviewHelpfulRepository.findByReviewAndUser(review, user)

        if (reviewHelpful == null) {
            val newReviewHelpful =
                ReviewHelpful(review = review, user = user, helpful = body.helpful)

            when (newReviewHelpful.helpful) {
                Helpful.BAD -> review.badCount++
                Helpful.NORMAL -> review.normalCount++
                Helpful.GOOD -> review.goodCount++
            }
            reviewHelpfulRepository.save(newReviewHelpful)
            return ReviewResponse.of(reviewRepository.save(review))
        }

        if (reviewHelpful.helpful == body.helpful) {
            reviewHelpfulRepository.delete(reviewHelpful)
            when (reviewHelpful.helpful) {
                Helpful.BAD -> review.badCount--
                Helpful.NORMAL -> review.normalCount--
                Helpful.GOOD -> review.goodCount--
            }
        } else {
            when (reviewHelpful.helpful) {
                Helpful.BAD -> review.badCount--
                Helpful.NORMAL -> review.normalCount--
                Helpful.GOOD -> review.goodCount--
            }

            when (body.helpful) {
                Helpful.BAD -> review.badCount++
                Helpful.NORMAL -> review.normalCount++
                Helpful.GOOD -> review.goodCount++
            }

            reviewHelpful.helpful = body.helpful
            reviewHelpfulRepository.save(reviewHelpful)
        }

        return ReviewResponse.of(reviewRepository.save(review))
    }
}