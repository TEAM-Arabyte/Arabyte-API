package com.arabyte.arabyteapi.domain.review.entity

import com.arabyte.arabyteapi.domain.review.enums.Helpful
import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ReviewHelpful(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    val review: Review,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    var helpful: Helpful
) : BaseEntity()