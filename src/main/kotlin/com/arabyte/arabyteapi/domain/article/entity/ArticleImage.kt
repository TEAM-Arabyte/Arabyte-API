package com.arabyte.arabyteapi.domain.article.entity

import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ArticleImage(
    val url: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article: Article
) : BaseEntity()