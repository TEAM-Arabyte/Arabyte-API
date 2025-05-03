package com.arabyte.arabyteapi.domain.review.dto

import com.arabyte.arabyteapi.domain.review.entity.Review
import com.arabyte.arabyteapi.domain.review.enums.*

class ReviewResponse(
    val reviewId: Int,
    val userId: Long,
    val companyName: String,
    val isCertified: Boolean,
    val star: Int,
    val text: String,
    val location: String,
    val category: String,
    var workIntensity: WorkIntensity,
    var workAtmosphere: WorkAtmosphere,
    var salary: Salary,
    var salaryDate: SalaryDate,
    var overtime: Overtime,
    var difficulty: WorkDifficulty,
) {
    companion object {
        fun of(review: Review): ReviewResponse {
            return ReviewResponse(
                reviewId = review.id.toInt(),
                userId = review.user.id,
                // todo company 구현 후 수정
                companyName = "회사이름",
                isCertified = review.isCertified,
                star = review.rating,
                text = review.text,
                location = review.location.toString(),
                category = review.category.name,
                workIntensity = review.workIntensity,
                workAtmosphere = review.workAtmosphere,
                salary = review.salary,
                salaryDate = review.salaryDate,
                overtime = review.overtime,
                difficulty = review.difficulty,
            )
        }
    }
}