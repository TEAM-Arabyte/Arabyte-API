package com.arabyte.arabyteapi.domain.article

import com.arabyte.arabyteapi.global.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class ArticleKind(
    val name: String,
    val description: String,
    val useAnonymous: Boolean
) : BaseEntity()