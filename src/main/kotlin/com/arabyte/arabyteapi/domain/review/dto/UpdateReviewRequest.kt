package com.arabyte.arabyteapi.domain.review.dto

import com.arabyte.arabyteapi.domain.review.enums.*

class UpdateReviewRequest(
    val category: Category,
    val text: String,
    val rating: Int,
    val workIntensity: WorkIntensity,
    val workAtmosphere: WorkAtmosphere,
    val salary: Salary,
    val salaryDate: SalaryDate,
    val overtime: Overtime,
    val difficulty: WorkDifficulty,
)