package com.arabyte.arabyteapi.domain.review.entity

import com.arabyte.arabyteapi.domain.company.Company
import com.arabyte.arabyteapi.domain.location.entity.Location
import com.arabyte.arabyteapi.domain.review.enums.*
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

// todo 필드들 확정되고 나서 구현하기

@Entity
class Review(
    val text: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location,
    var category: Category,
    var rating: Int,
    var workIntensity: WorkIntensity,
    var workAtmosphere: WorkAtmosphere,
    var salary: Salary,
    var salaryDate: SalaryDate,
    var overtime: Overtime,
    var difficulty: WorkDifficulty,
    var isCertified: Boolean = false,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) : BaseEntity()