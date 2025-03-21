package com.arabyte.arabyteapi.domain.article

import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class ArticleImage(
    val articleId: Long,
    val url: String
) : BaseEntity()