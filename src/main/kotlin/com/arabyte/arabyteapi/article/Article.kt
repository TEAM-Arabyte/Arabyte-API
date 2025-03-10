package com.arabyte.arabyteapi.article

import com.arabyte.arabyteapi.common.entity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity

@Entity
class Article(
    var title: String,
    var text: String,
    var readCount: Int,
    var likeCount: Int,
    @JsonIgnore
    val userId: Long,
    val articleKindId: Long
) : BaseEntity()
