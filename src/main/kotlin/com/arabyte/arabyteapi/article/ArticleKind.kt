package com.arabyte.arabyteapi.article

import com.arabyte.arabyteapi.common.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class ArticleKind(
    val name: String,
    val description: String,
    val useAnonymous: Boolean
) : BaseEntity()