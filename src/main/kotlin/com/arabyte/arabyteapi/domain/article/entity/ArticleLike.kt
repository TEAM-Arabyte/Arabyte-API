package com.arabyte.arabyteapi.domain.article.entity

import com.arabyte.arabyteapi.domain.user.entity.User
import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "article_like",
    uniqueConstraints = [UniqueConstraint(columnNames = ["article_id", "user_id"])]
)
class ArticleLike(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article: Article,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
) : BaseEntity()