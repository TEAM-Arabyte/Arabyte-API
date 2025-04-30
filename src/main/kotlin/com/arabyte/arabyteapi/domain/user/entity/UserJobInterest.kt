package com.arabyte.arabyteapi.domain.user.entity

import com.arabyte.arabyteapi.domain.review.enums.Category
import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_job_interest")
class UserJobInterest(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category_1")
    var category1: Category?,

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category_2")
    var category2: Category?,

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category_3")
    var category3: Category?
) : BaseEntity()