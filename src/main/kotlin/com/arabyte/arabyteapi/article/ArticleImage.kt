package com.arabyte.arabyteapi.article

import com.arabyte.arabyteapi.common.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class ArticleImage(
    val articleId: Long,
    val url: String
) : BaseEntity()